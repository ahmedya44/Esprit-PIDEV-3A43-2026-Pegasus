package com.pegasus.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class SpotifyService {
    
    private static final String CLIENT_ID = "your_client_id";
    private static final String CLIENT_SECRET = "your_client_secret";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    
    private HttpClient httpClient;
    
    public SpotifyService() {
        this.httpClient = HttpClient.newHttpClient();
        // Tenter de s'authentifier automatiquement au démarrage
        initializeService();
    }
    
    /**
     * Initialise le service Spotify avec authentification
     */
    private void initializeService() {
        try {
            // Authentification automatique avec tes vraies clés
            if (this.authenticate()) {
                System.out.println("Spotify Service initialise avec authentification reussie !");
            } else {
                System.out.println("Spotify Service initialise (mode simulation - authentification echouee)");
            }
        } catch (Exception e) {
            System.err.println("AVERTISSEMENT Erreur initialisation Spotify: " + e.getMessage());
        }
    }
    
    /**
     * Authentification avec Spotify API
     */
    public boolean authenticate() {
        try {
            String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                // Parser manuellement la réponse JSON simple
                String responseBody = response.body();
                if (responseBody.contains("\"access_token\"")) {
                    System.out.println("OK Spotify API authentifiee avec succes !");
                    return true;
                }
            } else {
                System.err.println("ERREUR auth Spotify: " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.err.println("ERREUR Exception auth Spotify: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Trouver une playlist basée sur l'ambiance de l'œuvre
     */
    public String findPlaylistForArtwork(String title, String description, String artist) {
        try {
            // Pour l'instant, retourner une playlist simulée
            // Plus tard: pourra faire un vrai appel API
            String mood = detectArtworkMood(title, description, artist);
            return generatePlaylistByMood(mood, title);
            
        } catch (Exception e) {
            System.err.println("ERREUR recherche playlist: " + e.getMessage());
        }
        
        return "Aucune playlist trouvée";
    }
    
    /**
     * Obtenir des recommendations basées sur une playlist
     */
    public String getRecommendations(String playlistId) {
        try {
            // Simulation de recommendations
            String[] recommendations = {
                "• Clair de Lune - Debussy",
                "• The Four Seasons - Vivaldi", 
                "• Canon in D - Pachelbel",
                "• Moonlight Sonata - Beethoven",
                "• Ave Maria - Schubert"
            };
            
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                result.append(recommendations[i]).append("\n");
            }
            
            return result.toString();
            
        } catch (Exception e) {
            System.err.println("ERREUR recommendations: " + e.getMessage());
        }
        
        return "Aucune recommendation";
    }
    
    /**
     * Détecter l'ambiance d'une œuvre
     */
    private String detectArtworkMood(String title, String description, String artist) {
        String titleLower = title.toLowerCase();
        String descLower = description != null ? description.toLowerCase() : "";
        
        // Romantique
        if (titleLower.contains("love") || titleLower.contains("amour") || 
            titleLower.contains("kiss") || titleLower.contains("couple")) {
            return "romantic";
        }
        
        // Nuit/Sombre
        if (titleLower.contains("night") || titleLower.contains("nuit") || 
            titleLower.contains("moon") || titleLower.contains("lune")) {
            return "night";
        }
        
        // Soleil/Joyeux
        if (titleLower.contains("sun") || titleLower.contains("soleil") || 
            titleLower.contains("summer") || titleLower.contains("été")) {
            return "happy";
        }
        
        // Moderne/Abstrait
        if (descLower.contains("abstract") || descLower.contains("abstrait") || 
            descLower.contains("modern") || descLower.contains("moderne")) {
            return "experimental";
        }
        
        // Classique
        if (descLower.contains("classical") || descLower.contains("classique") || 
            artist != null && (artist.contains("Mozart") || artist.contains("Beethoven"))) {
            return "classical";
        }
        
        // Par défaut
        return "neutral";
    }
    
    /**
     * Générer une playlist basée sur l'ambiance
     */
    private String generatePlaylistByMood(String mood, String artworkTitle) {
        switch (mood) {
            case "romantic":
                return "Romantic Art Gallery\nPerfect for " + artworkTitle + "\nClassical love songs & romantic piano";
                
            case "night":
                return "Night Time Art Vibes\nPerfect for " + artworkTitle + "\nAmbient & late night jazz";
                
            case "happy":
                return "Sunny Day Art Gallery\nPerfect for " + artworkTitle + "\nUpbeat classical & joyful melodies";
                
            case "experimental":
                return "Experimental Art Sounds\nPerfect for " + artworkTitle + "\nAvant-garde & modern classical";
                
            case "classical":
                return "Classical Art Masterpieces\nPerfect for " + artworkTitle + "\nBaroque, Renaissance & Classical periods";
                
            default:
                return "Art Gallery Focus Mix\nPerfect for " + artworkTitle + "\nConcentration & contemplation music";
        }
    }
    
    /**
     * Créer une playlist personnalisée pour une œuvre
     */
    public String createArtworkPlaylist(String artworkTitle) {
        try {
            String[] moods = {"Focus", "Relaxing", "Energetic", "Romantic", "Creative"};
            String randomMood = moods[(int)(Math.random() * moods.length)];
            
            return randomMood + " Art Gallery Mix\n" +
                   "Playlist generee pour: " + artworkTitle + "\n" +
                   "Perfect ambiance for viewing this artwork";
                   
        } catch (Exception e) {
            System.err.println("ERREUR creation playlist: " + e.getMessage());
            return "Playlist indisponible";
        }
    }
}
