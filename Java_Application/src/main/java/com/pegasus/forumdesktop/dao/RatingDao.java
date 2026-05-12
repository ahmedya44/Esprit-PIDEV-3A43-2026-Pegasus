package com.pegasus.forumdesktop.dao;

import com.pegasus.forumdesktop.config.DatabaseConfig;
import com.pegasus.forumdesktop.model.RatingSummary;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.OptionalDouble;

public class RatingDao {
    public RatingSummary summaryForPost(int postId) {
        String sql = "SELECT AVG(value) AS avg_value, COUNT(*) AS total FROM forum_post_rating WHERE post_id = ?";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (var rs = statement.executeQuery()) {
                rs.next();
                double average = rs.getDouble("avg_value");
                if (rs.wasNull()) {
                    average = 0.0;
                }
                return new RatingSummary(Math.round(average * 100.0) / 100.0, rs.getInt("total"));
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load rating summary.", ex);
        }
    }

    public void upsert(int postId, String raterEmail, double value) {
        String email = raterEmail == null ? "" : raterEmail.trim().toLowerCase();
        String update = "UPDATE forum_post_rating SET value = ?, updated_at = ? WHERE post_id = ? AND rater_email = ?";
        String insert = "INSERT INTO forum_post_rating (post_id, value, rater_email, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (var connection = DatabaseConfig.getConnection()) {
            try (var statement = connection.prepareStatement(update)) {
                statement.setDouble(1, value);
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                statement.setInt(3, postId);
                statement.setString(4, email);
                if (statement.executeUpdate() > 0) {
                    return;
                }
            }
            try (var statement = connection.prepareStatement(insert)) {
                statement.setInt(1, postId);
                statement.setDouble(2, value);
                statement.setString(3, email);
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                statement.setTimestamp(5, null);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not save rating.", ex);
        }
    }

    public OptionalDouble userRatingForPost(int postId, String raterEmail) {
        String email = raterEmail == null ? "" : raterEmail.trim().toLowerCase();
        if (email.isBlank()) {
            return OptionalDouble.empty();
        }
        String sql = "SELECT value FROM forum_post_rating WHERE post_id = ? AND rater_email = ? LIMIT 1";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            statement.setString(2, email);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return OptionalDouble.of(rs.getDouble("value"));
                }
                return OptionalDouble.empty();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load user rating.", ex);
        }
    }
}
