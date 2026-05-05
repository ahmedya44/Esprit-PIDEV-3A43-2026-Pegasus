package com.pegasus.forumdesktop.service;

import com.pegasus.forumdesktop.dao.CommentDao;
import com.pegasus.forumdesktop.dao.PostDao;
import com.pegasus.forumdesktop.dao.RatingDao;
import com.pegasus.forumdesktop.dao.TranslationDao;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.Comment;
import com.pegasus.forumdesktop.model.GifItem;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.model.RatingSummary;
import com.pegasus.forumdesktop.model.TranslationValue;
import com.pegasus.forumdesktop.model.User;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ForumService {
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final RatingDao ratingDao;
    private final TranslationDao translationDao;
    private final UserDao userDao;
    private final ModerationService moderationService;
    private final TranslationApiClient translationApiClient;
    private final AiAutocompleteClient aiAutocompleteClient;
    private final GifSearchClient gifSearchClient;

    public ForumService(PostDao postDao, CommentDao commentDao, RatingDao ratingDao, TranslationDao translationDao, UserDao userDao, ModerationService moderationService, TranslationApiClient translationApiClient, AiAutocompleteClient aiAutocompleteClient, GifSearchClient gifSearchClient) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.ratingDao = ratingDao;
        this.translationDao = translationDao;
        this.userDao = userDao;
        this.moderationService = moderationService;
        this.translationApiClient = translationApiClient;
        this.aiAutocompleteClient = aiAutocompleteClient;
        this.gifSearchClient = gifSearchClient;
    }

    public List<Post> visiblePosts(User viewer, String query, PostStatus statusFilter) {
        return postDao.findVisible(viewer, query, statusFilter);
    }

    public List<Comment> commentsForPost(int postId) {
        return commentDao.findByPost(postId);
    }

    public List<Comment> recentComments(String search) {
        return commentDao.findRecent(search, 100, 0);
    }

    public List<User> users() {
        return userDao.findAll();
    }

    public Post createPost(User actor, String title, String content, PostStatus status, String imageName, Set<Integer> allowedViewerIds) {
        requireUser(actor);
        String cleanTitle = requireText(title, "Title is required.");
        String cleanContent = requireText(content, "Content is required.");
        rejectForbidden(cleanTitle, cleanContent);

        Post post = new Post();
        post.setTitle(cleanTitle);
        post.setContent(cleanContent);
        post.setStatus(status == null ? PostStatus.OPEN : status);
        post.setOwnerId(actor.getId());
        post.setAuthorName(actor.getDisplayName());
        post.setAuthorEmail(actor.getEmail());
        post.setImageName(blankToNull(imageName));
        post.setAllowedViewerIds(sanitizeAllowedViewers(actor, post.getStatus(), allowedViewerIds));
        return postDao.insert(post);
    }

    public void updatePost(User actor, Post post, String title, String content, PostStatus status, String imageName, Set<Integer> allowedViewerIds) {
        requireUser(actor);
        requirePostManager(actor, post);
        String cleanTitle = requireText(title, "Title is required.");
        String cleanContent = requireText(content, "Content is required.");
        rejectForbidden(cleanTitle, cleanContent);

        post.setTitle(cleanTitle);
        post.setContent(cleanContent);
        post.setStatus(status == null ? PostStatus.OPEN : status);
        post.setImageName(blankToNull(imageName));
        if (post.getOwnerId() == null) {
            post.setOwnerId(actor.getId());
        }
        post.setAuthorName(post.getOwnerName() == null ? actor.getDisplayName() : post.getOwnerName());
        post.setAuthorEmail(actor.getEmail());
        post.setAllowedViewerIds(sanitizeAllowedViewers(actor, post.getStatus(), allowedViewerIds));
        postDao.update(post);
    }

    public void deletePost(User actor, Post post) {
        requireUser(actor);
        requirePostManager(actor, post);
        postDao.delete(post.getId());
    }

    public Comment addComment(User actor, Post post, String content, String gifUrl) {
        requireUser(actor);
        if (!canAccessPost(actor, post)) {
            throw new IllegalArgumentException("You cannot access this hidden post.");
        }
        if (!post.isOpen()) {
            throw new IllegalArgumentException("This post is closed. Comments are disabled.");
        }
        String cleanContent = content == null ? "" : content.trim();
        String cleanGif = gifUrl == null ? "" : gifUrl.trim();
        if (cleanContent.isBlank() && cleanGif.isBlank()) {
            throw new IllegalArgumentException("Add text or a GIF URL.");
        }
        rejectForbidden(cleanContent);

        Comment comment = new Comment();
        comment.setPostId(post.getId());
        comment.setContent(cleanContent);
        comment.setGifUrl(blankToNull(cleanGif));
        comment.setOwnerId(actor.getId());
        comment.setAuthorName(actor.getDisplayName());
        comment.setAuthorEmail(actor.getEmail());
        return commentDao.insert(comment);
    }

    public void updateComment(User actor, Comment comment, String content, String gifUrl) {
        requireUser(actor);
        requireCommentManager(actor, comment);
        String cleanContent = content == null ? "" : content.trim();
        String cleanGif = gifUrl == null ? "" : gifUrl.trim();
        if (cleanContent.isBlank() && cleanGif.isBlank()) {
            throw new IllegalArgumentException("Add text or a GIF URL.");
        }
        rejectForbidden(cleanContent);
        comment.setContent(cleanContent);
        comment.setGifUrl(blankToNull(cleanGif));
        commentDao.update(comment);
    }

    public void deleteComment(User actor, Comment comment) {
        requireUser(actor);
        requireCommentManager(actor, comment);
        commentDao.delete(comment.getId());
    }

    public void ratePost(User actor, Post post, double value) {
        requireUser(actor);
        if (!canAccessPost(actor, post)) {
            throw new IllegalArgumentException("You cannot rate this hidden post.");
        }
        if (value < 0.5 || value > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.5 and 5.");
        }
        ratingDao.upsert(post.getId(), actor.getEmail(), value);
    }

    public RatingSummary ratingSummary(int postId) {
        return ratingDao.summaryForPost(postId);
    }

    public TranslationValue translatedPost(Post post, String locale) {
        TranslationValue saved = translationDao.translatedPost(post.getId(), post.getTitle(), post.getContent(), locale);
        if (isOriginalLocale(locale)) {
            return saved;
        }
        String title = saved.title();
        String content = saved.content();
        if (sameText(title, post.getTitle())) {
            title = translationApiClient.translate(post.getTitle(), locale, "auto");
        }
        if (sameText(content, post.getContent())) {
            content = translationApiClient.translate(post.getContent(), locale, "auto");
        }
        return new TranslationValue(title, content);
    }

    public String translatedComment(Comment comment, String locale) {
        String saved = translationDao.translatedComment(comment.getId(), comment.getContent(), locale);
        if (isOriginalLocale(locale) || !sameText(saved, comment.getContent())) {
            return saved;
        }
        return translationApiClient.translate(comment.getContent(), locale, "auto");
    }

    public void savePostTranslation(Post post, String locale, String title, String content) {
        if (locale == null || locale.equals("orig")) {
            throw new IllegalArgumentException("Choose a target language.");
        }
        if (title != null && !title.isBlank()) {
            rejectForbidden(title);
            translationDao.upsert("post", String.valueOf(post.getId()), locale, "title", title.trim());
        }
        if (content != null && !content.isBlank()) {
            rejectForbidden(content);
            translationDao.upsert("post", String.valueOf(post.getId()), locale, "content", content.trim());
        }
    }

    public TranslationValue autoTranslatePost(Post post, String locale) {
        if (isOriginalLocale(locale)) {
            throw new IllegalArgumentException("Choose a target language first.");
        }
        return new TranslationValue(
            translationApiClient.translate(post.getTitle(), locale, "auto"),
            translationApiClient.translate(post.getContent(), locale, "auto")
        );
    }

    public String autoTranslateComment(Comment comment, String locale) {
        if (isOriginalLocale(locale)) {
            throw new IllegalArgumentException("Choose a target language first.");
        }
        return translationApiClient.translate(comment.getContent(), locale, "auto");
    }

    private boolean isOriginalLocale(String locale) {
        return locale == null || locale.isBlank() || locale.equalsIgnoreCase("orig");
    }

    private boolean sameText(String left, String right) {
        String cleanLeft = left == null ? "" : left.trim();
        String cleanRight = right == null ? "" : right.trim();
        return cleanLeft.equals(cleanRight);
    }

    public List<String> suggest(String field, String text, String context, String locale, int limit) {
        return aiAutocompleteClient.suggest(field, text, context, locale, limit);
    }

    public List<GifItem> searchGifs(String query, int limit) {
        return gifSearchClient.search(query, limit);
    }

    public void saveCommentTranslation(Comment comment, String locale, String content) {
        if (locale == null || locale.equals("orig")) {
            throw new IllegalArgumentException("Choose a target language.");
        }
        String cleanContent = requireText(content, "Translated content is required.");
        rejectForbidden(cleanContent);
        translationDao.upsert("commentaire", String.valueOf(comment.getId()), locale, "content", cleanContent);
    }

    public ForumStats stats() {
        return new ForumStats(postDao.countByStatus(), commentDao.totalCount(), postDao.topCommented(10));
    }

    public boolean canAccessPost(User viewer, Post post) {
        if (!post.isHidden()) {
            return true;
        }
        if (viewer == null) {
            return false;
        }
        if (viewer.isAdmin()) {
            return true;
        }
        if (post.getOwnerId() != null && post.getOwnerId() == viewer.getId()) {
            return true;
        }
        return post.getAllowedViewerIds().contains(viewer.getId());
    }

    public boolean canManagePost(User actor, Post post) {
        return actor != null && post != null && (actor.isAdmin() || (post.getOwnerId() != null && post.getOwnerId() == actor.getId()));
    }

    public boolean canManageComment(User actor, Comment comment) {
        return actor != null && comment != null && (actor.isAdmin() || (comment.getOwnerId() != null && comment.getOwnerId() == actor.getId()));
    }

    public Set<Integer> parseIds(String raw) {
        return parseAllowedViewers(raw);
    }

    public Set<Integer> parseAllowedViewers(String raw) {
        Set<Integer> ids = new LinkedHashSet<>();
        if (raw == null || raw.isBlank()) {
            return ids;
        }
        List<User> users = userDao.findAll();
        for (String part : raw.split("[,;\\R]+")) {
            String token = part.trim();
            if (token.isBlank()) {
                continue;
            }
            User matched = users.stream()
                .filter(user -> sameToken(token, user.getEmail())
                    || sameToken(token, user.getUsername())
                    || sameToken(token, user.getDisplayName()))
                .findFirst()
                .orElseGet(() -> token.matches("\\d+") ? userDao.findById(Integer.parseInt(token)).orElse(null) : null);
            if (matched == null) {
                throw new IllegalArgumentException("Allowed viewers must be existing user emails or usernames.");
            }
            ids.add(matched.getId());
        }
        return ids;
    }

    public String formatAllowedViewers(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return "";
        }
        return ids.stream()
            .map(id -> userDao.findById(id).map(User::getEmail).orElse(""))
            .filter(value -> !value.isBlank())
            .collect(java.util.stream.Collectors.joining(", "));
    }

    private boolean sameToken(String token, String value) {
        return value != null && value.trim().equalsIgnoreCase(token);
    }

    private Set<Integer> sanitizeAllowedViewers(User actor, PostStatus status, Set<Integer> rawIds) {
        if (status != PostStatus.HIDDEN || rawIds == null) {
            return Set.of();
        }
        Set<Integer> ids = new LinkedHashSet<>();
        for (Integer id : rawIds) {
            if (id != null && id > 0 && id != actor.getId() && userDao.findById(id).isPresent()) {
                ids.add(id);
            }
        }
        return ids;
    }

    private void requireUser(User actor) {
        if (actor == null) {
            throw new IllegalArgumentException("You must be logged in.");
        }
    }

    private void requirePostManager(User actor, Post post) {
        if (!canManagePost(actor, post)) {
            throw new IllegalArgumentException("Only the owner or an admin can modify this post.");
        }
    }

    private void requireCommentManager(User actor, Comment comment) {
        if (!canManageComment(actor, comment)) {
            throw new IllegalArgumentException("Only the owner or an admin can modify this comment.");
        }
    }

    private String requireText(String value, String message) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private void rejectForbidden(String... values) {
        for (String value : values) {
            if (moderationService.hasForbiddenWords(value)) {
                throw new IllegalArgumentException("The text contains forbidden words.");
            }
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public record ForumStats(List<PostDao.StatusCount> countsByStatus, int totalComments, List<PostDao.TopPost> topCommentedPosts) {
    }
}
