package com.pegasus.services;

import com.pegasus.entities.Art;
import com.pegasus.tools.dbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceArt {
    
    public ServiceArt() {
        createArtistColumnIfNotExists();
        // updateAllNullArtists(); // Désactivé pour permettre la gestion manuelle
    }
    
    private void createArtistColumnIfNotExists() {
        try (Connection conn = dbConnection.getConnection()) {
            // Vérifier si la colonne artist existe
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, "art", "artist")) {
                if (!rs.next()) {
                    // La colonne n'existe pas, l'ajouter
                    try (Statement stmt = conn.createStatement()) {
                        System.out.println("Ajout de la colonne 'artist' à la table art...");
                        stmt.execute("ALTER TABLE art ADD COLUMN artist VARCHAR(255)");
                        System.out.println("Colonne 'artist' ajoutée avec succès!");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification/création de la colonne artist: " + e.getMessage());
        }
    }
    
    private void updateAllNullArtists() {
        try (Connection conn = dbConnection.getConnection()) {
             Statement stmt = conn.createStatement()) {
                // Mettre à jour toutes les œuvres qui ont artist = NULL
                String updateSql = "UPDATE art SET artist = 'Artiste inconnu' WHERE artist IS NULL OR artist = '' OR artist = 'null'";
                int rowsUpdated = stmt.executeUpdate(updateSql);
                System.out.println("DEBUG - Mise à jour générale de " + rowsUpdated + " œuvre(s) sans artiste");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour générale: " + e.getMessage());
        }
    }
    
    public boolean createArt(Art art) {
        // Vérifier la connexion avant de sauvegarder
        try (Connection testConn = dbConnection.getConnection()) {
            if (testConn == null || testConn.isClosed()) {
                System.err.println("Connexion à la base de données non disponible");
                return false;
            }
            testConn.close();
        } catch (SQLException e) {
            System.err.println("Test de connexion échoué: " + e.getMessage());
            return false;
        }
        
        String sql = "INSERT INTO art (title, description, image_url, status, created_at, artist) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, art.getTitle());
            pstmt.setString(2, art.getDescription());
            pstmt.setString(3, art.getImageUrl());
            pstmt.setString(4, art.getStatus());
            pstmt.setTimestamp(5, Timestamp.valueOf(art.getCreatedAt()));
            pstmt.setString(6, art.getArtist());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    art.setId(generatedKeys.getInt(1));
                }
                System.out.println("Artwork added successfully!");
                return true;
            } else {
                System.err.println("Failed to insert artwork");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error creating art: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<Art> getArtById(int id) {
        String sql = "SELECT * FROM art WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToArt(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting art by id: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public List<Art> getAllArts() {
        List<Art> arts = new ArrayList<>();
        String sql = "SELECT * FROM art ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                arts.add(mapResultSetToArt(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all arts: " + e.getMessage());
            e.printStackTrace();
        }
        
        return arts;
    }
    
    public List<Art> getArtsByStatus(String status) {
        List<Art> arts = new ArrayList<>();
        String sql = "SELECT * FROM art WHERE status = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    arts.add(mapResultSetToArt(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting arts by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return arts;
    }
    
    public boolean updateArt(Art art) {
        String sql = "UPDATE art SET title = ?, description = ?, image_url = ?, status = ?, artist = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, art.getTitle());
            pstmt.setString(2, art.getDescription());
            pstmt.setString(3, art.getImageUrl());
            pstmt.setString(4, art.getStatus());
            pstmt.setString(5, art.getArtist());
            pstmt.setInt(6, art.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating art: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteArt(int id) {
        String sql = "DELETE FROM art WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting art: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean incrementLikes(int artId) {
        String sql = "UPDATE art SET likes = likes + 1 WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error incrementing likes: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean decrementLikes(int artId) {
        String sql = "UPDATE art SET likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE id = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, artId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error decrementing likes: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private Art mapResultSetToArt(ResultSet rs) throws SQLException {
        Art art = new Art();
        art.setId(rs.getInt("id"));
        art.setTitle(rs.getString("title"));
        art.setDescription(rs.getString("description"));
        art.setImageUrl(rs.getString("image_url"));
        art.setStatus(rs.getString("status"));
        
        // Récupérer l'artiste
        try {
            String artist = rs.getString("artist");
            System.out.println("DEBUG - Artiste récupéré pour ID " + art.getId() + ": " + artist);
            art.setArtist(artist != null && !artist.trim().isEmpty() ? artist : "Artiste inconnu");
        } catch (SQLException e) {
            // Si la colonne artist n'existe pas encore
            System.out.println("DEBUG - Colonne artist non trouvée pour ID " + art.getId());
            art.setArtist("Artiste inconnu");
        }
        
        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            art.setCreatedAt(timestamp.toLocalDateTime());
        }
        
        // Récupérer les likes (nouveau champ)
        try {
            art.setLikes(rs.getInt("likes"));
        } catch (SQLException e) {
            // Si la colonne likes n'existe pas encore, mettre 0 par défaut
            art.setLikes(0);
        }
        
        return art;
    }
}
