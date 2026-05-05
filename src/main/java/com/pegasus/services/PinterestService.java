package com.pegasus.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PinterestService {
    
    private static final String PINTEREST_API_BASE = "https://api.pinterest.com/v1";
    private static final String APP_ID = "1565705"; // Ta clé API
    private final HttpClient httpClient;
    
    public PinterestService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    
    /**
     * Recherche des inspirations artistiques sur Pinterest
     * @param query Terme de recherche (ex: "abstract painting", "portrait art")
     * @return Liste d'inspirations artistiques
     */
    public Optional<List<ArtInspiration>> searchArtInspirations(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Optional.empty();
        }
        
        try {
            // Construire l'URL de recherche Pinterest
            String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
            String url = String.format("%s/search/pins?query=%s&access_token=%s&limit=10", 
                    PINTEREST_API_BASE, encodedQuery, APP_ID);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return parsePinterestResponse(response.body());
            } else {
                System.err.println("Pinterest API error: " + response.statusCode());
                return Optional.empty();
            }
            
        } catch (Exception e) {
            System.err.println("Pinterest search error: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    /**
     * Parse la réponse JSON de Pinterest API
     */
    private Optional<List<ArtInspiration>> parsePinterestResponse(String jsonResponse) {
        try {
            List<ArtInspiration> inspirations = new ArrayList<>();
            
            // Parsing simple sans librairie JSON externe
            String[] pinData = extractJsonArray(jsonResponse, "data");
            if (pinData != null) {
                for (String pin : pinData) {
                    if (pin.contains("\"id\"")) {
                        String id = extractJsonValue(pin, "id");
                        String note = extractJsonValue(pin, "note");
                        String imageUrl = extractNestedJsonValue(pin, "image", "original", "url");
                        
                        if (imageUrl != null && note != null) {
                            inspirations.add(new ArtInspiration(id, note, imageUrl));
                        }
                    }
                }
            }
            
            return inspirations.isEmpty() ? Optional.empty() : Optional.of(inspirations);
            
        } catch (Exception e) {
            System.err.println("Error parsing Pinterest response: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    /**
     * Extrait un tableau JSON simple
     */
    private String[] extractJsonArray(String json, String key) {
        String searchPattern = "\"" + key + "\":[";
        int startIndex = json.indexOf(searchPattern);
        if (startIndex == -1) return null;
        
        startIndex += searchPattern.length();
        int endIndex = findMatchingBracket(json, startIndex, '[', ']');
        if (endIndex == -1) return null;
        
        String arrayContent = json.substring(startIndex, endIndex);
        return arrayContent.split("\\},\\{");
    }
    
    /**
     * Trouve la bracket correspondante
     */
    private int findMatchingBracket(String str, int start, char open, char close) {
        int count = 0;
        for (int i = start; i < str.length(); i++) {
            if (str.charAt(i) == open) count++;
            else if (str.charAt(i) == close) count--;
            if (count == 0) return i;
        }
        return -1;
    }
    
    /**
     * Extrait une valeur JSON simple
     */
    private String extractJsonValue(String json, String key) {
        String searchPattern = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchPattern);
        if (startIndex == -1) return null;
        
        startIndex += searchPattern.length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) return null;
        
        return json.substring(startIndex, endIndex);
    }
    
    /**
     * Extrait une valeur JSON imbriquée
     */
    private String extractNestedJsonValue(String json, String parentKey, String childKey, String targetKey) {
        String parentPattern = "\"" + parentKey + "\":{";
        int parentStart = json.indexOf(parentPattern);
        if (parentStart == -1) return null;
        
        parentStart += parentPattern.length();
        int parentEnd = findMatchingBracket(json, parentStart, '{', '}');
        if (parentEnd == -1) return null;
        
        String parentContent = json.substring(parentStart, parentEnd);
        
        String childPattern = "\"" + childKey + "\":{";
        int childStart = parentContent.indexOf(childPattern);
        if (childStart == -1) return null;
        
        childStart += childPattern.length();
        int childEnd = findMatchingBracket(parentContent, childStart, '{', '}');
        if (childEnd == -1) return null;
        
        String childContent = parentContent.substring(childStart, childEnd);
        return extractJsonValue(childContent, targetKey);
    }
    
    /**
     * Génère des suggestions basées sur le titre de l'œuvre
     */
    public Optional<List<ArtInspiration>> getInspirationsForArtwork(String artworkTitle, String artworkDescription) {
        // Extraire des mots-clés du titre et de la description
        String keywords = extractArtKeywords(artworkTitle, artworkDescription);
        
        // Rechercher sur Pinterest
        return searchArtInspirations(keywords);
    }
    
    /**
     * Extrait des mots-clés artistiques
     */
    private String extractArtKeywords(String title, String description) {
        StringBuilder keywords = new StringBuilder();
        
        if (title != null) {
            keywords.append(title).append(" ");
        }
        
        if (description != null) {
            keywords.append(description).append(" ");
        }
        
        // Ajouter des termes artistiques pertinents
        String[] artTerms = {"art", "painting", "artwork", "artist", "gallery", "museum", 
                           "portrait", "landscape", "abstract", "modern", "contemporary"};
        
        for (String term : artTerms) {
            keywords.append(term).append(" ");
        }
        
        return keywords.toString().trim();
    }
    
    /**
     * Classe interne pour représenter une inspiration artistique
     */
    public static class ArtInspiration {
        private final String id;
        private final String title;
        private final String imageUrl;
        
        public ArtInspiration(String id, String title, String imageUrl) {
            this.id = id;
            this.title = title;
            this.imageUrl = imageUrl;
        }
        
        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getImageUrl() { return imageUrl; }
        
        @Override
        public String toString() {
            return "ArtInspiration{id='" + id + "', title='" + title + "', imageUrl='" + imageUrl + "'}";
        }
    }
}
