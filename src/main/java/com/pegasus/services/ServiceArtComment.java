package com.pegasus.services;

import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtComment {
    
    public ServiceArtComment() {
        // Créer la table automatiquement si elle n'existe pas
        createTableIfNotExists();
        // Ajouter la colonne parent_id pour les réponses
        createParentIdColumnIfNotExists();
    }
    
    // Ajouter un champ parent_id pour les réponses
    private void createParentIdColumnIfNotExists() {
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Simplement ajouter la colonne parent_id si elle n'existe pas
            try {
                stmt.execute("ALTER TABLE art_comment ADD COLUMN parent_id INT DEFAULT NULL");
            } catch (SQLException e) {
                if (e.getMessage() != null && e.getMessage().contains("Duplicate column name")) {
                    return;
                } else {
                    System.err.println("Erreur lors de l'ajout de la colonne parent_id: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }
    
    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS art_comment (
                id INT AUTO_INCREMENT PRIMARY KEY,
                art_id INT NOT NULL,
                username VARCHAR(100) NOT NULL,
                content TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE
            )
            """;
        
        try (Connection conn = dbConnection.getConnection();
            Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            
        } catch (SQLException e) {
            System.err.println("Erreur création table commentaires: " + e.getMessage());
        }
    }
    
    public boolean addComment(int artId, String username, String content) {
        String sql = "INSERT INTO art_comment (art_id, username, content, created_at) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, username);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur ajout commentaire: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<Comment> getCommentsByArtId(int artId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM art_comment WHERE art_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setArtId(rs.getInt("art_id"));
                comment.setUsername(rs.getString("username"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                comments.add(comment);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur récupération commentaires: " + e.getMessage());
        }
        
        return comments;
    }
    
    public int getCommentCount(int artId) {
        String sql = "SELECT COUNT(*) FROM art_comment WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur comptage commentaires: " + e.getMessage());
        }
        
        return 0;
    }
    
    public boolean deleteComment(int commentId) {
        String sql = "DELETE FROM art_comment WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, commentId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur suppression commentaire: " + e.getMessage());
        }
        
        return false;
    }
    
    // Classe interne pour les commentaires
    public static class Comment {
        private int id;
        private int artId;
        private String username;
        private String content;
        private LocalDateTime createdAt;
        
        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public int getArtId() { return artId; }
        public void setArtId(int artId) { this.artId = artId; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        @Override
        public String toString() {
            return "Comment{id=" + id + ", username='" + username + "', content='" + content + "'}";
        }
    }
}
