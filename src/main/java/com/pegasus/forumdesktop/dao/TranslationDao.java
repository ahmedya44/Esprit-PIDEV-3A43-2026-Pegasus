package com.pegasus.forumdesktop.dao;

import com.pegasus.forumdesktop.config.DatabaseConfig;
import com.pegasus.forumdesktop.model.TranslationValue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TranslationDao {
    public TranslationValue translatedPost(int postId, String fallbackTitle, String fallbackContent, String locale) {
        if (isOriginal(locale)) {
            return new TranslationValue(fallbackTitle, fallbackContent);
        }
        Map<String, String> fields = fields("post", String.valueOf(postId), locale);
        return new TranslationValue(fields.getOrDefault("title", fallbackTitle), fields.getOrDefault("content", fallbackContent));
    }

    public String translatedComment(int commentId, String fallbackContent, String locale) {
        if (isOriginal(locale)) {
            return fallbackContent;
        }
        return fields("commentaire", String.valueOf(commentId), locale).getOrDefault("content", fallbackContent);
    }

    public void upsert(String objectType, String objectId, String locale, String field, String value) {
        String update = """
            UPDATE translation
            SET value = ?
            WHERE object_type = ? AND object_id = ? AND locale = ? AND field = ?
            """;
        String insert = """
            INSERT INTO translation (object_type, object_id, locale, field, value)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (var connection = DatabaseConfig.getConnection()) {
            try (var statement = connection.prepareStatement(update)) {
                statement.setString(1, value);
                statement.setString(2, objectType);
                statement.setString(3, objectId);
                statement.setString(4, locale);
                statement.setString(5, field);
                if (statement.executeUpdate() > 0) {
                    return;
                }
            }
            try (var statement = connection.prepareStatement(insert)) {
                statement.setString(1, objectType);
                statement.setString(2, objectId);
                statement.setString(3, locale);
                statement.setString(4, field);
                statement.setString(5, value);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not save translation.", ex);
        }
    }

    private Map<String, String> fields(String type, String id, String locale) {
        String sql = "SELECT field, value FROM translation WHERE object_type = ? AND object_id = ? AND locale = ?";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            statement.setString(2, id);
            statement.setString(3, locale);
            try (var rs = statement.executeQuery()) {
                Map<String, String> fields = new HashMap<>();
                while (rs.next()) {
                    fields.put(rs.getString("field"), rs.getString("value"));
                }
                return fields;
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load translation.", ex);
        }
    }

    private boolean isOriginal(String locale) {
        return locale == null || locale.isBlank() || "orig".equalsIgnoreCase(locale);
    }
}
