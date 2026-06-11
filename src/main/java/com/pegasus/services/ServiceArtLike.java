package com.pegasus.services;

import com.pegasus.entities.ArtLike;
import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtLike {

    public ServiceArtLike() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS art_like (
                id INT AUTO_INCREMENT PRIMARY KEY,
                art_id INT NOT NULL,
                session_id VARCHAR(180) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (art_id) REFERENCES art(id) ON DELETE CASCADE,
                UNIQUE KEY unique_art_session (art_id, session_id)
            )
            """;

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            try {
                stmt.execute("ALTER TABLE art ADD COLUMN likes INT DEFAULT 0");
            } catch (SQLException e) {
                if (!e.getMessage().contains("Duplicate column name")) {
                    System.err.println("Could not prepare art likes column: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Could not prepare art likes table: " + e.getMessage());
        }
    }
    
    public boolean addLike(int artId, String sessionId) {
        if (hasLiked(artId, sessionId)) {
            return false;
        }
        
        String sql = "INSERT INTO art_like (art_id, session_id, created_at) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updateArtLikeCount(artId);
            }
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding like: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean removeLike(int artId, String sessionId) {
        String sql = "DELETE FROM art_like WHERE art_id = ? AND session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updateArtLikeCount(artId);
            }
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing like: " + e.getMessage());
        }
        
        return false;
    }

    private void updateArtLikeCount(int artId) {
        String sql = "UPDATE art SET likes = COALESCE((SELECT COUNT(*) FROM art_like WHERE art_id = ?), 0) WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artId);
            pstmt.setInt(2, artId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating like count: " + e.getMessage());
        }
    }
    
    public boolean hasLiked(int artId, String sessionId) {
        String sql = "SELECT COUNT(*) FROM art_like WHERE art_id = ? AND session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking like: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<ArtLike> getSessionLikes(String sessionId) {
        List<ArtLike> likes = new ArrayList<>();
        String sql = "SELECT * FROM art_like WHERE session_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, sessionId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                likes.add(mapResultSetToArtLike(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting session likes: " + e.getMessage());
        }
        
        return likes;
    }
    
    public List<ArtLike> getArtLikes(int artId) {
        List<ArtLike> likes = new ArrayList<>();
        String sql = "SELECT * FROM art_like WHERE art_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                likes.add(mapResultSetToArtLike(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art likes: " + e.getMessage());
        }
        
        return likes;
    }
    
    public int getSessionLikeCount(String sessionId) {
        String sql = "SELECT COUNT(*) FROM art_like WHERE session_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, sessionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting session like count: " + e.getMessage());
        }
        
        return 0;
    }
    
    public int getArtLikeCount(int artId) {
        String sql = "SELECT COUNT(*) FROM art_like WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art like count: " + e.getMessage());
        }
        
        return 0;
    }
    
    public boolean toggleLike(int artId, String sessionId) {
        if (hasLiked(artId, sessionId)) {
            return removeLike(artId, sessionId);
        } else {
            // Si l'utilisateur avait déjà un dislike, le supprimer d'abord
            ServiceArtDislike dislikeService = new ServiceArtDislike();
            if (dislikeService.hasDisliked(artId, sessionId)) {
                dislikeService.removeDislike(artId, sessionId);
            }
            return addLike(artId, sessionId);
        }
    }
    
    public java.util.Map<Integer, Integer> getCoLikedArtCounts(int artId) {
        java.util.Map<Integer, Integer> counts = new java.util.HashMap<>();
        String sql = """
            SELECT al2.art_id, COUNT(*) AS co_count
            FROM art_like al1
            JOIN art_like al2 ON al1.session_id = al2.session_id AND al1.art_id <> al2.art_id
            WHERE al1.art_id = ?
            GROUP BY al2.art_id
            ORDER BY co_count DESC
            """;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                counts.put(rs.getInt("art_id"), rs.getInt("co_count"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting co-liked arts: " + e.getMessage());
        }

        return counts;
    }

    public List<Integer> getMostLikedArts(int limit) {
        List<Integer> artIds = new ArrayList<>();
        String sql = "SELECT art_id, COUNT(*) as like_count FROM art_like " +
                    "GROUP BY art_id ORDER BY like_count DESC LIMIT ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                artIds.add(rs.getInt("art_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting most liked arts: " + e.getMessage());
        }
        
        return artIds;
    }
    
    public void cleanupOldLikes(LocalDateTime cutoffDate) {
        String sql = "DELETE FROM art_like WHERE created_at < ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(cutoffDate));
            int deletedRows = pstmt.executeUpdate();
            
            if (deletedRows > 0) {
                System.out.println("Cleaned up " + deletedRows + " old likes");
            }
            
        } catch (SQLException e) {
            System.err.println("Error cleaning up old likes: " + e.getMessage());
        }
    }
    
    private ArtLike mapResultSetToArtLike(ResultSet rs) throws SQLException {
        ArtLike like = new ArtLike();
        like.setId(rs.getInt("id"));
        like.setArtId(rs.getInt("art_id"));
        like.setSessionId(rs.getString("session_id"));
        
        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            like.setCreatedAt(timestamp.toLocalDateTime());
        }
        
        return like;
    }
}
