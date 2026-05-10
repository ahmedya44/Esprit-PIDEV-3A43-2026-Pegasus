package com.pegasus.controllers.front;


import com.pegasus.controllers.SceneNavigator;
import com.pegasus.forumdesktop.controller.ForumController;
import com.pegasus.forumdesktop.dao.CommentDao;
import com.pegasus.forumdesktop.dao.PostDao;
import com.pegasus.forumdesktop.dao.RatingDao;
import com.pegasus.forumdesktop.dao.TranslationDao;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.service.AiAutocompleteClient;
import com.pegasus.forumdesktop.service.ForumService;
import com.pegasus.forumdesktop.service.GifSearchClient;
import com.pegasus.forumdesktop.service.ModerationService;
import com.pegasus.forumdesktop.service.TranslationApiClient;
import com.pegasus.forumdesktop.view.ForumView;
import com.pegasus.entities.User;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import java.util.Optional;

public final class ForumModuleLauncher {
    private ForumModuleLauncher() {
    }

    public static void openForumWindow() {
        User appUser = SceneNavigator.getCurrentUser();
        if (appUser == null) {
            try {
                SceneNavigator.goTo("/views/front/signin-view.fxml");
            } catch (Exception e) {
                showAlert("Forum Navigation Error", "Unable to open sign-in page.", e.getMessage());
            }
            return;
        }

        if (appUser.getEmail() == null || appUser.getEmail().isBlank()) {
            showAlert("Forum Navigation Error", "Unable to open forum because the signed-in user has no email.", null);
            return;
        }

        try {
            SceneNavigator.goTo("/views/front/forum-view.fxml");
        } catch (Exception e) {
            showAlert("Forum Launch Error", "Could not open the forum page.", e.getMessage());
        }
    }

    public static Parent buildForumContentForFrontPage() {
        User appUser = SceneNavigator.getCurrentUser();
        if (appUser == null) {
            throw new IllegalStateException("A signed-in user is required to open the forum.");
        }

        UserDao userDao = new UserDao();
        ForumService forumService = createForumService(userDao);
        ForumView view = new ForumView();
        view.setInternalHeaderVisible(false);
        ForumController controller = new ForumController(view, forumService);
        com.pegasus.forumdesktop.model.User forumUser = resolveForumUser(userDao, appUser);
        controller.bootstrapWithForumUser(forumUser);
        return view.getRoot();
    }

    public static ForumService createForumService(UserDao userDao) {
        PostDao postDao = new PostDao(userDao);
        CommentDao commentDao = new CommentDao(userDao);
        RatingDao ratingDao = new RatingDao();
        TranslationDao translationDao = new TranslationDao();
        return new ForumService(
                postDao,
                commentDao,
                ratingDao,
                translationDao,
                userDao,
                new ModerationService(),
                new TranslationApiClient(),
                new AiAutocompleteClient(),
                new GifSearchClient()
        );
    }

    public static com.pegasus.forumdesktop.model.User resolveForumUserForCurrentSession(UserDao userDao) {
        User appUser = SceneNavigator.getCurrentUser();
        if (appUser == null) {
            throw new IllegalStateException("No signed-in user.");
        }
        if (appUser.getEmail() == null || appUser.getEmail().isBlank()) {
            throw new IllegalStateException("Signed-in user has no email.");
        }
        return resolveForumUser(userDao, appUser);
    }

    private static com.pegasus.forumdesktop.model.User resolveForumUser(UserDao userDao, User appUser) {
        try {
            Optional<com.pegasus.forumdesktop.model.User> existing = userDao.findByEmail(appUser.getEmail());
            if (existing.isPresent()) {
                return existing.get();
            }
        } catch (Exception ignored) {
            // Fallback to session user mapping when DB lookup fails.
        }

        com.pegasus.forumdesktop.model.User fallback = new com.pegasus.forumdesktop.model.User();
        fallback.setId(appUser.getId() == null ? 0 : appUser.getId());
        fallback.setEmail(appUser.getEmail());
        fallback.setUsername(appUser.getUsername());
        fallback.setDtype(appUser.getDtype());
        fallback.setStatus(appUser.getStatus());
        fallback.setRolesJson(appUser.getRoles());
        return fallback;
    }

    private static void showAlert(String title, String header, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(details == null ? "Please try again or contact support." : details);
        alert.showAndWait();
    }
}
