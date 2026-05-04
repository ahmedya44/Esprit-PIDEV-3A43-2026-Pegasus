package com.pegasus.services;

import com.pegasus.entities.ArtLike;
import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtLike {
    
    public boolean addLike(int artId, String sessionId) {
        // Check if already liked
        if (hasLiked(artId, sessionId)) {
            System.out.println("Art already liked by this session");
            return false;
        }
        
        String sql = "INSERT INTO art_like (art_id, session_id, created_at) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, sessionId);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding like: " + e.getMessage());
            e.printStackTrace();
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
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing like: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean toggleLike(int artId, String sessionId) {
        if (hasLiked(artId, sessionId)) {
            return removeLike(artId, sessionId);
        } else {
            return addLike(artId, sessionId);
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
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
