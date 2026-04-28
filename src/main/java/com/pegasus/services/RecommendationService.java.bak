package com.pegasus.services;

import com.pegasus.entities.Art;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Arrays;

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
            System.out.println("Analyse des recommandations pour: " + currentArtwork.getTitle());
            System.out.println("Total d'oeuvres disponibles: " + allArtworks.size());
            
            for (Art art : allArtworks) {
                if (art.getId() != artworkId) {
                    double score = calculateSimilarityScore(currentArtwork, art);
                    System.out.println("Score " + art.getTitle() + " - Score: " + String.format("%.2f", score));
                    
                    if (score >= 0.7) { // Seuil TRES strict - minimum 70% de similarité
                        scoredRecommendations.add(new ArtworkScore(art, score));
                        System.out.println("Ajoute aux recommandations!");
                    } else {
                        System.out.println("Score trop bas: " + String.format("%.2f", score));
                    }
                }
            }
            
            System.out.println("Recommandations trouvees: " + scoredRecommendations.size());
            
            // Si aucune recommandation n'est trouvée, prendre les œuvres les plus populaires
            if (scoredRecommendations.isEmpty()) {
                System.out.println("Aucune recommandation trouvee, utilisation des oeuvres populaires...");
                
                List<Art> popularArtworks = allArtworks.stream()
                    .filter(art -> art.getId() != artworkId)
                    .sorted((a, b) -> Integer.compare(b.getLikes(), a.getLikes()))
                    .limit(limit)
                    .collect(Collectors.toList());
                
                System.out.println("Oeuvres populaires selectionnees: " + popularArtworks.size());
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
        
        System.out.println("Comparaison: " + art1.getTitle() + " vs " + art2.getTitle());
        
        // SEUL CRITERE IMPORTANT: Même artiste
        if (art1.getArtist() != null && art2.getArtist() != null) {
            String artist1 = art1.getArtist().toLowerCase().trim();
            String artist2 = art2.getArtist().toLowerCase().trim();
            
            if (artist1.equals(artist2)) {
                score = 1.0; // 100% si même artiste
                System.out.println("Meme artiste: " + art1.getArtist());
            } else {
                score = 0.0; // 0% si artiste différent
                System.out.println("Artistes differents: " + art1.getArtist() + " != " + art2.getArtist());
            }
        } else {
            score = 0.0; // 0% si pas d'artiste
            System.out.println("Pas d'information sur l'artiste");
        }
        
        System.out.println("Score final: " + String.format("%.2f", score));
        System.out.println("---");
        
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
     * Recommendation basée sur les tendances
     * Simple: retourne les œuvres avec le plus de likes récents
     */
    public List<Art> getTrendingArtworks(int limit) {
        // Pour l'instant, même logique que popularité
        // Plus tard: pourrait analyser les likes par date
        return getPopularArtworks(limit);
    }
}
