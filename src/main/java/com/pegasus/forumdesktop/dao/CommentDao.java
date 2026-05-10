package com.pegasus.forumdesktop.dao;

import com.pegasus.forumdesktop.config.DatabaseConfig;
import com.pegasus.forumdesktop.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDao {
    private final UserDao userDao;

    public CommentDao(UserDao userDao) {
        this.userDao = userDao;
        ensureBanColumn();
    }

    public List<Comment> findByPost(int postId) {
        String sql = """
            SELECT c.*, u.username AS owner_username
            FROM forum_commentaire c
            LEFT JOIN `user` u ON u.id = c.owner_id
            WHERE c.post_id = ?
            ORDER BY c.created_at DESC
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (var rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(map(rs));
                }
                return comments;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load comments.", ex);
        }
    }

    public List<Comment> findRecent(String search, int limit, int offset) {
        StringBuilder sql = new StringBuilder("""
            SELECT c.*, u.username AS owner_username
            FROM forum_commentaire c
            LEFT JOIN `user` u ON u.id = c.owner_id
            WHERE 1 = 1
            """);
        boolean hasSearch = search != null && !search.isBlank();
        if (hasSearch) {
            sql.append(" AND (LOWER(c.content) LIKE ? OR LOWER(c.author_name) LIKE ? OR LOWER(c.author_email) LIKE ?)");
        }
        sql.append(" ORDER BY c.created_at DESC LIMIT ? OFFSET ?");

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (hasSearch) {
                String like = "%" + search.trim().toLowerCase() + "%";
                statement.setString(index++, like);
                statement.setString(index++, like);
                statement.setString(index++, like);
            }
            statement.setInt(index++, limit);
            statement.setInt(index, offset);
            try (var rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(map(rs));
                }
                return comments;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load recent comments.", ex);
        }
    }

    public Optional<Comment> findById(int id) {
        String sql = """
            SELECT c.*, u.username AS owner_username
            FROM forum_commentaire c
            LEFT JOIN `user` u ON u.id = c.owner_id
            WHERE c.id = ?
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (var rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load comment.", ex);
        }
    }

    public Comment insert(Comment comment) {
        String sql = """
            INSERT INTO forum_commentaire
            (post_id, content, author_name, author_email, gif_url, owner_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, comment.getPostId());
            statement.setString(2, comment.getContent());
            statement.setString(3, comment.getAuthorName());
            statement.setString(4, comment.getAuthorEmail());
            statement.setString(5, blankToNull(comment.getGifUrl()));
            if (comment.getOwnerId() == null) {
                statement.setNull(6, java.sql.Types.INTEGER);
            } else {
                statement.setInt(6, comment.getOwnerId());
            }
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(8, null);
            statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    comment.setId(keys.getInt(1));
                }
            }
            return comment;
        } catch (SQLException ex) {
            throw new DaoException("Could not create comment.", ex);
        }
    }

    public void update(Comment comment) {
        String sql = """
            UPDATE forum_commentaire
            SET content = ?, author_name = ?, author_email = ?, gif_url = ?, owner_id = ?, updated_at = ?
            WHERE id = ?
            """;
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, comment.getContent());
            statement.setString(2, comment.getAuthorName());
            statement.setString(3, comment.getAuthorEmail());
            statement.setString(4, blankToNull(comment.getGifUrl()));
            if (comment.getOwnerId() == null) {
                statement.setNull(5, java.sql.Types.INTEGER);
            } else {
                statement.setInt(5, comment.getOwnerId());
            }
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(7, comment.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Could not update comment.", ex);
        }
    }

    public void delete(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement("DELETE FROM forum_commentaire WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Could not delete comment.", ex);
        }
    }

    public int totalCount() {
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement("SELECT COUNT(*) FROM forum_commentaire");
             var rs = statement.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            throw new DaoException("Could not count comments.", ex);
        }
    }

    public void setBannedByAdmin(int commentId, boolean banned) {
        String sql = "UPDATE forum_commentaire SET is_banned = ?, updated_at = ? WHERE id = ?";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, banned);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3, commentId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Could not update comment moderation state.", ex);
        }
    }

    private Comment map(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setContent(rs.getString("content"));
        comment.setAuthorName(rs.getString("author_name"));
        comment.setAuthorEmail(rs.getString("author_email"));
        comment.setGifUrl(rs.getString("gif_url"));
        comment.setOwnerId(JdbcMapper.nullableInt(rs, "owner_id"));
        comment.setOwnerName(rs.getString("owner_username"));
        comment.setCreatedAt(JdbcMapper.dateTime(rs, "created_at"));
        comment.setUpdatedAt(JdbcMapper.dateTime(rs, "updated_at"));
        comment.setBannedByAdmin(rs.getBoolean("is_banned"));
        return comment;
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private void ensureBanColumn() {
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate("ALTER TABLE forum_commentaire ADD COLUMN is_banned TINYINT(1) NOT NULL DEFAULT 0");
        } catch (SQLException ignored) {
            // Column already exists or migration is managed externally.
        }
    }
}
