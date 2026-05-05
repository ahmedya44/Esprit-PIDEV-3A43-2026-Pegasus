package com.pegasus.services;

import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtDislike {
    
    public ServiceArtDislike() {
        // Créer la table automatiquement si elle n'existe pas
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS art_dislike (
                id INT AUTO_INCREMENT PRIMARY KEY,
                art_id INT NOT NULL,
                session_id VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE,
                UNIQUE KEY unique_art_session (art_id, session_id)
            )
            """;
        
        String addColumnSQL = """
            ALTER TABLE art ADD COLUMN IF NOT EXISTS dislikes INT DEFAULT 0
            """;
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Créer la table art_dislike
            stmt.execute(createTableSQL);
            System.out.println("✅ Table art_dislike créée ou déjà existante");
            
            // Ajouter la colonne dislikes
            try {
                stmt.execute(addColumnSQL);
                System.out.println("✅ Colonne dislikes ajoutée ou déjà existante");
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate column name")) {
                    System.err.println("Erreur ajout colonne dislikes: " + e.getMessage());
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur création table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public boolean addDislike(int artId, String sessionId) {
        // Check if already disliked
        if (hasDisliked(artId, sessionId)) {
            System.out.println("Art already disliked by this session");
            return false;
        }
        
        String sql = "INSERT INTO art_dislike (art_id, session_id, created_at) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            
            // Mettre à jour le compteur de dislikes dans la table art
            if (affectedRows > 0) {
                updateArtDislikeCount(artId);
            }
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding dislike: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void updateArtDislikeCount(int artId) {
        // D'abord essayer de mettre à jour avec COUNT
        String sql = "UPDATE art SET dislikes = COALESCE((SELECT COUNT(*) FROM art_dislike WHERE art_id = ?), 0) WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setInt(2, artId);
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("✅ Updated dislike count for art " + artId + ": " + affectedRows + " rows affected");
            
        } catch (SQLException e) {
            System.err.println("❌ Error updating dislike count: " + e.getMessage());
            e.printStackTrace();
            
            // Si ça échoue, essayer une mise à jour simple
            try {
                String simpleSQL = "UPDATE art SET dislikes = dislikes + 1 WHERE id = ?";
                try (Connection conn2 = dbConnection.getConnection();
                     PreparedStatement pstmt2 = conn2.prepareStatement(simpleSQL)) {
                    
                    pstmt2.setInt(1, artId);
                    int rows = pstmt2.executeUpdate();
                    System.out.println("✅ Simple increment for art " + artId + ": " + rows + " rows affected");
                }
            } catch (SQLException e2) {
                System.err.println("❌ Even simple update failed: " + e2.getMessage());
            }
        }
    }
    
    public boolean hasDisliked(int artId, String sessionId) {
        String sql = "SELECT COUNT(*) FROM art_dislike WHERE art_id = ? AND session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking dislike: " + e.getMessage());
        }
        
        return false;
    }
    
    public int getDislikeCount(int artId) {
        String sql = "SELECT COUNT(*) FROM art_dislike WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting dislike count: " + e.getMessage());
        }
        
        return 0;
    }
    
    public boolean removeDislike(int artId, String sessionId) {
        String sql = "DELETE FROM art_dislike WHERE art_id = ? AND session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing dislike: " + e.getMessage());
            return false;
        }
    }
    
    public List<Integer> getDislikedArtIds(String sessionId) {
        List<Integer> dislikedArtIds = new ArrayList<>();
        String sql = "SELECT art_id FROM art_dislike WHERE session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, sessionId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dislikedArtIds.add(rs.getInt("art_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting disliked arts: " + e.getMessage());
        }
        
        return dislikedArtIds;
    }
}
