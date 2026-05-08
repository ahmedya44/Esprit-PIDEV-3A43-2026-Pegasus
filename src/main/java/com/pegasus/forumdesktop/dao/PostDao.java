package com.pegasus.forumdesktop.dao;

import com.pegasus.forumdesktop.config.DatabaseConfig;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PostDao {
    private final UserDao userDao;

    public PostDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<Post> findVisible(User viewer, String query, PostStatus statusFilter) {
        StringBuilder sql = new StringBuilder("""
            SELECT p.*, u.username AS owner_username
            FROM forum_post p
            LEFT JOIN `user` u ON u.id = p.owner_id
            WHERE 1 = 1
            """);
        List<Object> params = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            sql.append(" AND (LOWER(p.title) LIKE ? OR LOWER(p.content) LIKE ? OR LOWER(p.author_name) LIKE ?)");
            String like = "%" + query.trim().toLowerCase() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (statusFilter != null) {
            sql.append(" AND p.status = ?");
            params.add(statusFilter.name());
        }
        if (viewer == null) {
            sql.append(" AND p.status <> 'HIDDEN'");
        } else if (!viewer.isAdmin()) {
            sql.append("""
                 AND (p.status <> 'HIDDEN'
                    OR p.owner_id = ?
                    OR EXISTS (SELECT 1 FROM forum_post_allowed_viewer av WHERE av.post_id = p.id AND av.user_id = ?))
                """);
            params.add(viewer.getId());
            params.add(viewer.getId());
        }
        sql.append(" ORDER BY p.created_at DESC");

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql.toString())) {
            bind(statement, params);
            try (var rs = statement.executeQuery()) {
                List<Post> posts = new ArrayList<>();
                while (rs.next()) {
                    posts.add(withAllowedViewers(map(rs)));
                }
                return posts;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load posts.", ex);
        }
    }

    public Optional<Post> findById(int id) {
        String sql = """
            SELECT p.*, u.username AS owner_username
            FROM forum_post p
            LEFT JOIN `user` u ON u.id = p.owner_id
            WHERE p.id = ?
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (var rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(withAllowedViewers(map(rs))) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load post.", ex);
        }
    }

    public Post insert(Post post) {
        String sql = """
            INSERT INTO forum_post
            (title, content, author_name, author_email, owner_id, status, created_at, updated_at, image_name)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setString(3, post.getAuthorName());
            statement.setString(4, post.getAuthorEmail());
            if (post.getOwnerId() == null) {
                statement.setNull(5, java.sql.Types.INTEGER);
            } else {
                statement.setInt(5, post.getOwnerId());
            }
            statement.setString(6, post.getStatus().name());
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(8, null);
            statement.setString(9, post.getImageName());
            statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    post.setId(keys.getInt(1));
                }
            }
            replaceAllowedViewers(post.getId(), post.getAllowedViewerIds());
            return post;
        } catch (SQLException ex) {
            throw new DaoException("Could not create post.", ex);
        }
    }

    public void update(Post post) {
        String sql = """
            UPDATE forum_post
            SET title = ?, content = ?, author_name = ?, author_email = ?, owner_id = ?, status = ?, updated_at = ?, image_name = ?
            WHERE id = ?
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setString(3, post.getAuthorName());
            statement.setString(4, post.getAuthorEmail());
            if (post.getOwnerId() == null) {
                statement.setNull(5, java.sql.Types.INTEGER);
            } else {
                statement.setInt(5, post.getOwnerId());
            }
            statement.setString(6, post.getStatus().name());
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(8, post.getImageName());
            statement.setInt(9, post.getId());
            statement.executeUpdate();
            replaceAllowedViewers(post.getId(), post.getAllowedViewerIds());
        } catch (SQLException ex) {
            throw new DaoException("Could not update post.", ex);
        }
    }

    public void delete(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement("DELETE FROM forum_post WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Could not delete post.", ex);
        }
    }

    public Set<Integer> allowedViewerIds(int postId) {
        String sql = "SELECT user_id FROM forum_post_allowed_viewer WHERE post_id = ? ORDER BY user_id";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (var rs = statement.executeQuery()) {
                Set<Integer> ids = new LinkedHashSet<>();
                while (rs.next()) {
                    ids.add(rs.getInt("user_id"));
                }
                return ids;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load allowed viewers.", ex);
        }
    }

    public void replaceAllowedViewers(int postId, Set<Integer> viewerIds) {
        try (var connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);
            try (var delete = connection.prepareStatement("DELETE FROM forum_post_allowed_viewer WHERE post_id = ?")) {
                delete.setInt(1, postId);
                delete.executeUpdate();
            }
            if (viewerIds != null && !viewerIds.isEmpty()) {
                try (var insert = connection.prepareStatement("INSERT INTO forum_post_allowed_viewer (post_id, user_id) VALUES (?, ?)")) {
                    for (Integer userId : viewerIds) {
                        if (userId == null || userDao.findById(userId).isEmpty()) {
                            continue;
                        }
                        insert.setInt(1, postId);
                        insert.setInt(2, userId);
                        insert.addBatch();
                    }
                    insert.executeBatch();
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            throw new DaoException("Could not save allowed viewers.", ex);
        }
    }

    public List<StatusCount> countByStatus() {
        String sql = "SELECT status, COUNT(*) AS total FROM forum_post GROUP BY status";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql);
             var rs = statement.executeQuery()) {
            List<StatusCount> counts = new ArrayList<>();
            while (rs.next()) {
                counts.add(new StatusCount(PostStatus.fromDatabase(rs.getString("status")), rs.getInt("total")));
            }
            return counts;
        } catch (SQLException ex) {
            throw new DaoException("Could not load post stats.", ex);
        }
    }

    public List<TopPost> topCommented(int limit) {
        String sql = """
            SELECT p.*, u.username AS owner_username, COUNT(c.id) AS comments_count
            FROM forum_post p
            LEFT JOIN `user` u ON u.id = p.owner_id
            LEFT JOIN forum_commentaire c ON c.post_id = p.id
            GROUP BY p.id
            ORDER BY comments_count DESC, p.created_at DESC
            LIMIT ?
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (var rs = statement.executeQuery()) {
                List<TopPost> posts = new ArrayList<>();
                while (rs.next()) {
                    posts.add(new TopPost(map(rs), rs.getInt("comments_count")));
                }
                return posts;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load top posts.", ex);
        }
    }

    private Post withAllowedViewers(Post post) {
        post.setAllowedViewerIds(allowedViewerIds(post.getId()));
        return post;
    }

    private Post map(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setAuthorName(rs.getString("author_name"));
        post.setAuthorEmail(rs.getString("author_email"));
        post.setOwnerId(JdbcMapper.nullableInt(rs, "owner_id"));
        post.setOwnerName(rs.getString("owner_username"));
        post.setStatus(PostStatus.fromDatabase(rs.getString("status")));
        post.setCreatedAt(JdbcMapper.dateTime(rs, "created_at"));
        post.setUpdatedAt(JdbcMapper.dateTime(rs, "updated_at"));
        post.setImageName(rs.getString("image_name"));
        return post;
    }

    private void bind(java.sql.PreparedStatement statement, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof Integer value) {
                statement.setInt(i + 1, value);
            } else {
                statement.setString(i + 1, String.valueOf(param));
            }
        }
    }

    public record StatusCount(PostStatus status, int count) {
    }

    public record TopPost(Post post, int commentsCount) {
    }
}
