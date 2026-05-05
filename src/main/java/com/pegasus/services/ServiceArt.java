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
    
    public boolean createArt(Art art) {
        String sql = "INSERT INTO art (title, description, image_url, status, created_at, artist) VALUES (?, ?, ?, ?, ?, ?)";
        
        System.out.println("🗄️ Requête SQL INSERT:");
        System.out.println("  📌 Titre: " + art.getTitle());
        System.out.println("  📝 Description: " + art.getDescription());
        System.out.println("  🖼️  Image URL: " + art.getImageUrl());
        System.out.println("  📊 Statut: " + art.getStatus());
        System.out.println("  📅 Créé le: " + art.getCreatedAt());
        System.out.println("  👨 Artiste: " + art.getArtist());
        
        try (Connection conn = dbConnection.getConnection()) {
            System.out.println("✅ Connexion à la base de données réussie");
            
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, art.getTitle());
            pstmt.setString(2, art.getDescription());
            pstmt.setString(3, art.getImageUrl());
            pstmt.setString(4, art.getStatus());
            pstmt.setTimestamp(5, Timestamp.valueOf(art.getCreatedAt()));
            pstmt.setString(6, art.getArtist());
            
            int affectedRows = pstmt.executeUpdate();
            System.out.println("📊 affectedRows: " + affectedRows);
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    art.setId(generatedId);
                    System.out.println("✅ Œuvre insérée avec ID: " + generatedId);
                    System.out.println("🎨 Artwork added successfully!");
                    return true;
                } else {
                    System.out.println("❌ Pas d'ID généré");
                }
            } else {
                System.out.println("❌ Aucune ligne insérée");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de l'ajout: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
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
        
        System.out.println("🔍 Requête SQL: " + sql);
        
        try (Connection conn = dbConnection.getConnection()) {
            System.out.println("✅ Connexion réussie pour getAllArts");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                Art art = mapResultSetToArt(rs);
                arts.add(art);
                count++;
                
                System.out.println("📌 Œuvre #" + count + ":");
                System.out.println("  ID: " + art.getId());
                System.out.println("  Titre: " + art.getTitle());
                System.out.println("  Statut: " + art.getStatus());
                System.out.println("  Créé: " + art.getCreatedAt());
                System.out.println("  Artiste: " + art.getArtist());
                System.out.println("  ---");
            }
            
            System.out.println("📊 Total d'œuvres trouvées: " + count);
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL dans getAllArts: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Si la base de données est vide, créer des données de test
        if (arts.isEmpty()) {
            System.out.println("⚠️ Aucune œuvre trouvée, création de données de test...");
            arts = createSampleData();
        }
        
        return arts;
    }
    
    private List<Art> createSampleData() {
        List<Art> sampleArts = new ArrayList<>();
        
        // Créer quelques œuvres d'exemple
        Art art1 = new Art();
        art1.setId(1);
        art1.setTitle("Starry Night");
        art1.setDescription("Une peinture emblématique de Van Gogh montrant un ciel nocturne tourbillonnant.");
        art1.setImageUrl("https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=400");
        art1.setStatus("published");
        art1.setArtist("Vincent van Gogh");
        art1.setCreatedAt(LocalDateTime.now().minusDays(30));
        art1.setLikes(156);
        sampleArts.add(art1);
        
        Art art2 = new Art();
        art2.setId(2);
        art2.setTitle("The Persistence of Memory");
        art2.setDescription("Les fameuses montres molles de Salvador Dali représentant le temps qui se déforme.");
        art2.setImageUrl("https://images.unsplash.com/photo-1579532585038-5b5bfecfd4c6?w=400");
        art2.setStatus("published");
        art2.setArtist("Salvador Dalí");
        art2.setCreatedAt(LocalDateTime.now().minusDays(25));
        art2.setLikes(203);
        sampleArts.add(art2);
        
        Art art3 = new Art();
        art3.setId(3);
        art3.setTitle("The Great Wave");
        art3.setDescription("La vague célèbre de Hokusai avec le Mont Fuji en arrière-plan.");
        art3.setImageUrl("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400");
        art3.setStatus("pending");
        art3.setArtist("Katsushika Hokusai");
        art3.setCreatedAt(LocalDateTime.now().minusDays(20));
        art3.setLikes(178);
        sampleArts.add(art3);
        
        Art art4 = new Art();
        art4.setId(4);
        art4.setTitle("Girl with a Pearl Earring");
        art4.setDescription("Portrait mystérieux d'une jeune fille avec un éclairage dramatique.");
        art4.setImageUrl("https://images.unsplash.com/photo-1549490349-8643362247b5?w=400");
        art4.setStatus("published");
        art4.setArtist("Johannes Vermeer");
        art4.setCreatedAt(LocalDateTime.now().minusDays(15));
        art4.setLikes(145);
        sampleArts.add(art4);
        
        Art art5 = new Art();
        art5.setId(5);
        art5.setTitle("The Scream");
        art5.setDescription("Figure emblématique de l'angoisse existentielle moderne.");
        art5.setImageUrl("https://images.unsplash.com/photo-1578321272176-b7bbc0679853?w=400");
        art5.setStatus("pending");
        art5.setArtist("Edvard Munch");
        art5.setCreatedAt(LocalDateTime.now().minusDays(10));
        art5.setLikes(189);
        sampleArts.add(art5);
        
        System.out.println("Créé " + sampleArts.size() + " œuvres de test");
        return sampleArts;
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
