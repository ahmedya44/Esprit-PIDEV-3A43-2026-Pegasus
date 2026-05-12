package com.pegasus.services;

import com.pegasus.entities.Art;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {
    
    private ServiceArt serviceArt;
    
    public RecommendationService() {
        this.serviceArt = new ServiceArt();
    }
    
    /**
     * Trouve des œuvres VRAIMENT similaires basées sur plusieurs critères
     * Logique : "Les gens qui aiment cette œuvre aiment aussi..."
     */
    public List<Art> getSimilarArtworks(int artworkId, int limit) {
        try {
            // Récupérer toutes les œuvres avec leurs likes
            List<Art> allArtworks = serviceArt.getAllArts();
            
            // Trouver l'œuvre actuelle
            Art currentArtwork = null;
            for (Art art : allArtworks) {
                if (art.getId() == artworkId) {
                    currentArtwork = art;
                    break;
                }
            }
            
            if (currentArtwork == null) {
                return new ArrayList<>();
            }
            
            // Liste pour stocker les recommandations avec scores
            List<ArtworkScore> scoredRecommendations = new ArrayList<>();
            
            // Analyser chaque œuvre pour calculer un score de similarité
            
            for (Art art : allArtworks) {
                if (art.getId() != artworkId) {
                    double score = calculateSimilarityScore(currentArtwork, art);
                    
                    if (score >= 0.7) { // Seuil TRÈS strict - minimum 70% de similarité
                        scoredRecommendations.add(new ArtworkScore(art, score));
                    }
                }
            }
            
            
            // Si aucune recommandation n'est trouvée, prendre les œuvres les plus populaires
            if (scoredRecommendations.isEmpty()) {
                
                List<Art> popularArtworks = allArtworks.stream()
                    .filter(art -> art.getId() != artworkId)
                    .sorted((a, b) -> Integer.compare(b.getLikes(), a.getLikes()))
                    .limit(limit)
                    .collect(Collectors.toList());
                
                return popularArtworks;
            }
            
            // Trier par score de similarité (du plus similaire au moins similaire)
            scoredRecommendations.sort((a, b) -> Double.compare(b.score, a.score));
            
            // Retourner uniquement les œuvres les plus similaires
            return scoredRecommendations.stream()
                .limit(limit)
                .map(as -> as.artwork)
                .collect(Collectors.toList());
            
        } catch (Exception e) {
            System.err.println("Error getting recommendations: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Calcule un score de similarité entre deux œuvres
     * ALGORITHME TRÈS STRICT - uniquement de vraies similarités
     */
    private double calculateSimilarityScore(Art art1, Art art2) {
        double score = 0.0;
        
        
        // SEUL CRITÈRE IMPORTANT: Même artiste
        if (art1.getArtist() != null && art2.getArtist() != null) {
            String artist1 = art1.getArtist().toLowerCase().trim();
            String artist2 = art2.getArtist().toLowerCase().trim();
            
            if (artist1.equals(artist2)) {
                score = 1.0; // 100% si même artiste
            } else {
                score = 0.0; // 0% si artiste différent
            }
        } else {
            score = 0.0; // 0% si pas d'information sur l'artiste
        }
        
        
        return score;
    }
    
    /**
     * Classe interne pour stocker une œuvre avec son score de similarité
     */
    private static class ArtworkScore {
        Art artwork;
        double score;
        
        ArtworkScore(Art artwork, double score) {
            this.artwork = artwork;
            this.score = score;
        }
    }
    
    /**
     * Trouve les œuvres les plus populaires
     */
    public List<Art> getPopularArtworks(int limit) {
        try {
            List<Art> allArtworks = serviceArt.getAllArts();
            
            return allArtworks.stream()
                .sorted((a, b) -> Integer.compare(b.getLikes(), a.getLikes()))
                .limit(limit)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.err.println("Error getting popular artworks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Recommandation basée sur les tendances
     * Simple: retourne les œuvres avec le plus de likes récents
     */
    public List<Art> getTrendingArtworks(int limit) {
        // Pour l'instant, même logique que popularité
        return getPopularArtworks(limit);
    }
}
