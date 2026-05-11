package com.pegasus.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ArtistsService {
    
    private static final String WIKIPEDIA_API = "https://fr.wikipedia.org/api/rest_v1/page/summary/";
    private static final String EN_WIKIPEDIA_API = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    
    private HttpClient httpClient;
    
    // Biographies locales pour fallback
    private static final String[] LOCAL_BIOS = {
        "Vincent van Gogh (1853-1890) était un peintre néerlandais post-impressionniste. Ses œuvres les plus célèbres incluent Les Tournesols, La Nuit étoilée et Les Mangeurs de pommes de terre.",
        "Pablo Picasso (1881-1973) était un peintre, sculpteur et céramiste espagnol. Il est le fondateur du cubisme et l'un des artistes les plus influents du 20ème siècle.",
        "Claude Monet (1840-1926) était un peintre français et fondateur de l'impressionnisme. Ses séries des Nymphéas et des Cathédrales de Rouen sont mondialement connues.",
        "Léonard de Vinci (1452-1519) était un peintre, sculpteur, architecte et scientifique italien. La Joconde et La Dernière Cène sont ses œuvres les plus célèbres.",
        "Henri Matisse (1869-1954) était un peintre, sculpteur et dessinateur français. Il est considéré comme l'un des plus grands artistes français du 20ème siècle.",
        "Auguste Rodin (1840-1917) était un sculpteur français. Le Penseur et Le Baiser sont parmi ses créations les plus célèbres.",
        "Paul Cézanne (1839-1906) était un peintre français post-impressionniste. Il est considéré comme le père de l'art moderne.",
        "Andy Warhol (1928-1987) était un artiste américain et figure majeure du pop art. Ses œuvres sur Marilyn Monroe et les boîtes de soupe Campbell sont iconiques.",
        "Salvador Dalí (1904-1989) était un peintre surréaliste espagnol. La Persistance de la mémoire est son œuvre la plus célèbre.",
        "Frida Kahlo (1907-1954) était une peintre mexicaine. Ses autoportraits expriment sa douleur et ses expériences personnelles."
    };
    
    public ArtistsService() {
        this.httpClient = HttpClient.newHttpClient();
    }
    
    /**
     * Obtenir la biographie d'un artiste depuis Wikipedia
     */
    public String getArtistBiography(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            return "❌ Veuillez entrer un nom d'artiste valide.";
        }
        
        try {
            // Essayer Wikipedia français d'abord
            String frenchResult = getFromWikipedia(artistName, true);
            if (frenchResult != null && !frenchResult.contains("not found")) {
                return frenchResult;
            }
            
            // Essayer Wikipedia anglais
            String englishResult = getFromWikipedia(artistName, false);
            if (englishResult != null && !englishResult.contains("not found")) {
                return englishResult;
            }
            
            // Fallback avec biographie locale
            return getLocalBiography(artistName);
            
        } catch (Exception e) {
            System.err.println("Erreur Artists API: " + e.getMessage());
            return getLocalBiography(artistName);
        }
    }
    
    /**
     * Récupérer depuis Wikipedia API
     */
    private String getFromWikipedia(String artistName, boolean french) {
        try {
            String encodedName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
            String apiUrl = french ? WIKIPEDIA_API + encodedName : EN_WIKIPEDIA_API + encodedName;
            
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json")
                .header("User-Agent", "Pegasus-Gallery/1.0 (Educational Project)")
                .timeout(java.time.Duration.ofSeconds(10))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            
            if (response.statusCode() == 200) {
                return parseWikipediaResponse(response.body(), artistName, french);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur Wikipedia: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Parser la réponse Wikipedia
     */
    private String parseWikipediaResponse(String json, String artistName, boolean french) {
        try {
            // Parser manuel simple du JSON
            if (json.contains("\"extract\":")) {
                String extract = json.split("\"extract\":\"")[1].split("\"")[0];
                String title = json.split("\"title\":\"")[1].split("\"")[0];
                
                // Nettoyer le texte
                extract = extract.replace("\\n", " ").replace("\\\"", "\"");
                
                if (extract.length() > 50) {
                    String source = french ? "📚 Source: Wikipedia (Français)" : "📚 Source: Wikipedia (English)";
                    return "🎨 " + title + "\n\n" + extract + "\n\n" + source;
                }
            }
        } catch (Exception e) {
            // Erreur de parsing
        }
        
        return null;
    }
    
    /**
     * Biographie locale (fallback)
     */
    private String getLocalBiography(String artistName) {
        // Chercher une correspondance locale
        String lowerName = artistName.toLowerCase();
        
        for (String bio : LOCAL_BIOS) {
            if (bio.toLowerCase().contains(lowerName.split(" ")[0])) {
                return bio + "\n\n📚 Source: Base de données locale";
            }
        }
        
        // Si aucune correspondance, retourner une biographie générique
        return "🎨 " + artistName + "\n\n" + 
               "Biographie non disponible dans notre base de données locale. " +
               "Essayez avec un nom plus connu comme 'Van Gogh', 'Picasso' ou 'Monet'.\n\n" +
               "📚 Source: Base de données locale";
    }
    
    /**
     * Vérifier si un artiste est disponible
     */
    public boolean isArtistAvailable(String artistName) {
        String result = getArtistBiography(artistName);
        return result != null && !result.contains("❌") && !result.contains("non disponible");
    }
    
    /**
     * Obtenir une biographie formatée
     */
    public String getFormattedBiography(String artistName) {
        return getArtistBiography(artistName);
    }
    
    /**
     * Suggestions d'artistes populaires
     */
    public String[] getPopularArtists() {
        return new String[]{
            "Vincent van Gogh", "Pablo Picasso", "Claude Monet", 
            "Léonard de Vinci", "Henri Matisse", "Andy Warhol",
            "Salvador Dalí", "Frida Kahlo", "Auguste Rodin", "Paul Cézanne"
        };
    }
    
    /**
     * Statistiques du service
     */
    public String getServiceStatus() {
        return "🎨 Artists Service\n" +
               "📚 Sources: Wikipedia FR/EN + Base locale\n" +
               "🌍 Couverture: Artistes mondiaux\n" +
               "📊 Disponibilité: " + (isApiAvailable() ? "✅ En ligne" : "📚 Local uniquement");
    }
    
    /**
     * Vérifier la disponibilité de l'API
     */
    public boolean isApiAvailable() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WIKIPEDIA_API + "Vincent_van_Gogh"))
                .timeout(java.time.Duration.ofSeconds(3))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            return false;
        }
    }
}
