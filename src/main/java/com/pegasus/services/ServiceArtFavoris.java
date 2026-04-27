package com.pegasus.services;

import com.pegasus.entities.ArtFavoris;
import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceArtFavoris {
    
    public boolean addToFavoris(int artId, String userIdentifier) {
        // Check if already in favoris
        if (isInFavoris(artId, userIdentifier)) {
            System.out.println("Art already in favoris for this user");
            return false;
        }
        
        String sql = "INSERT INTO art_favoris (art_id, user_identifier, added_at) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, userIdentifier);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding to favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean removeFromFavoris(int artId, String userIdentifier) {
        String sql = "DELETE FROM art_favoris WHERE art_id = ? AND user_identifier = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, userIdentifier);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error removing from favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean isInFavoris(int artId, String userIdentifier) {
        String sql = "SELECT COUNT(*) FROM art_favoris WHERE art_id = ? AND user_identifier = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, userIdentifier);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public List<ArtFavoris> getUserFavoris(String userIdentifier) {
        List<ArtFavoris> favoris = new ArrayList<>();
        String sql = "SELECT * FROM art_favoris WHERE user_identifier = ? ORDER BY added_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userIdentifier);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                favoris.add(mapResultSetToArtFavoris(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return favoris;
    }
    
    public List<ArtFavoris> getArtFavoris(int artId) {
        List<ArtFavoris> favoris = new ArrayList<>();
        String sql = "SELECT * FROM art_favoris WHERE art_id = ? ORDER BY added_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                favoris.add(mapResultSetToArtFavoris(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art favoris: " + e.getMessage());
            e.printStackTrace();
        }
        
        return favoris;
    }
    
    public int getUserFavorisCount(String userIdentifier) {
        String sql = "SELECT COUNT(*) FROM art_favoris WHERE user_identifier = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userIdentifier);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user favoris count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getArtFavorisCount(int artId) {
        String sql = "SELECT COUNT(*) FROM art_favoris WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art favoris count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean toggleFavoris(int artId, String userIdentifier) {
        if (isInFavoris(artId, userIdentifier)) {
            return removeFromFavoris(artId, userIdentifier);
        } else {
            return addToFavoris(artId, userIdentifier);
        }
    }
    
    private ArtFavoris mapResultSetToArtFavoris(ResultSet rs) throws SQLException {
        ArtFavoris favoris = new ArtFavoris();
        favoris.setId(rs.getInt("id"));
        favoris.setArtId(rs.getInt("art_id"));
        favoris.setUserIdentifier(rs.getString("user_identifier"));
        
        Timestamp timestamp = rs.getTimestamp("added_at");
        if (timestamp != null) {
            favoris.setAddedAt(timestamp.toLocalDateTime());
        }
        
        return favoris;
    }
}
