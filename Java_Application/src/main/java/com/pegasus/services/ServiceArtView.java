package com.pegasus.services;

import com.pegasus.entities.ArtView;
import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtView {
    
    public boolean recordView(int artId, String ipAddress) {
        String sql = "INSERT INTO art_view (art_id, ip_address, viewed_at) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, ipAddress);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error recording view: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public List<ArtView> getArtViews(int artId) {
        List<ArtView> views = new ArrayList<>();
        String sql = "SELECT * FROM art_view WHERE art_id = ? ORDER BY viewed_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                views.add(mapResultSetToArtView(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art views: " + e.getMessage());
            e.printStackTrace();
        }
        
        return views;
    }
    
    public List<ArtView> getIpViews(String ipAddress) {
        List<ArtView> views = new ArrayList<>();
        String sql = "SELECT * FROM art_view WHERE ip_address = ? ORDER BY viewed_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ipAddress);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                views.add(mapResultSetToArtView(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting IP views: " + e.getMessage());
            e.printStackTrace();
        }
        
        return views;
    }
    
    public int getArtViewCount(int artId) {
        String sql = "SELECT COUNT(*) FROM art_view WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting art view count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getIpViewCount(String ipAddress) {
        String sql = "SELECT COUNT(*) FROM art_view WHERE ip_address = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ipAddress);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting IP view count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getTotalViews() {
        String sql = "SELECT COUNT(*) FROM art_view";
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total views: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public List<Integer> getMostViewedArts(int limit) {
        List<Integer> artIds = new ArrayList<>();
        String sql = "SELECT art_id, COUNT(*) as view_count FROM art_view " +
                    "GROUP BY art_id ORDER BY view_count DESC LIMIT ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                artIds.add(rs.getInt("art_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting most viewed arts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return artIds;
    }
    
    public List<ArtView> getRecentViews(int limit) {
        List<ArtView> views = new ArrayList<>();
        String sql = "SELECT * FROM art_view ORDER BY viewed_at DESC LIMIT ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                views.add(mapResultSetToArtView(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting recent views: " + e.getMessage());
            e.printStackTrace();
        }
        
        return views;
    }
    
    public List<ArtView> getViewsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<ArtView> views = new ArrayList<>();
        String sql = "SELECT * FROM art_view WHERE viewed_at BETWEEN ? AND ? ORDER BY viewed_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                views.add(mapResultSetToArtView(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting views by date range: " + e.getMessage());
            e.printStackTrace();
        }
        
        return views;
    }
    
    public boolean hasViewedRecently(int artId, String ipAddress, int minutes) {
        String sql = "SELECT COUNT(*) FROM art_view " +
                    "WHERE art_id = ? AND ip_address = ? " +
                    "AND viewed_at >= DATE_SUB(NOW(), INTERVAL ? MINUTE)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            pstmt.setString(2, ipAddress);
            pstmt.setInt(3, minutes);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking recent view: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public void cleanupOldViews(LocalDateTime cutoffDate) {
        String sql = "DELETE FROM art_view WHERE viewed_at < ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(cutoffDate));
            int deletedRows = pstmt.executeUpdate();
            
            if (deletedRows > 0) {
                System.out.println("Cleaned up " + deletedRows + " old views");
            }
            
        } catch (SQLException e) {
            System.err.println("Error cleaning up old views: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public int getUniqueViewCount(int artId) {
        String sql = "SELECT COUNT(DISTINCT ip_address) FROM art_view WHERE art_id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting unique view count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    private ArtView mapResultSetToArtView(ResultSet rs) throws SQLException {
        ArtView view = new ArtView();
        view.setId(rs.getInt("id"));
        view.setArtId(rs.getInt("art_id"));
        view.setIpAddress(rs.getString("ip_address"));
        
        Timestamp timestamp = rs.getTimestamp("viewed_at");
        if (timestamp != null) {
            view.setViewedAt(timestamp.toLocalDateTime());
        }
        
        return view;
    }
}
