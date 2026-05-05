package com.pegasus.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class FactsService {
    
    private static final String API_URL = "https://uselessfacts.jsph.pl/api/v2/facts/random";
    private static final String[] ART_FACTS = {
        "Le tableau le plus cher du monde est le Salvator Mundi de Léonard de Vinci, vendu à 450 millions de dollars en 2017.",
        "La Joconde a été volée du Louvre en 1911 et n'a été retrouvée que deux ans plus tard.",
        "Vincent van Gogh n'a vendu qu'un seul tableau de son vivant : La Vigne Rouge.",
        "L'art abstrait a été inventé par Wassily Kandinsky en 1910.",
        "Le plus petit tableau au monde mesure seulement 0.3 x 0.2 pouces.",
        "Les peintures de Van Gogh sont parmi les plus chères au monde, mais il est mort dans la pauvreté.",
        "Le Louvre contient plus de 35 000 œuvres d'art, mais seulement 10% sont exposées.",
        "La Mona Lisa n'a pas de sourcils, car à l'époque les femmes de haute société les rasaient.",
        "Picasso a créé plus de 50 000 œuvres d'art au cours de sa vie.",
        "La première photographie a été prise en 1826 et nécessitait 8 heures d'exposition."
    };
    
    private HttpClient httpClient;
    private Random random = new Random();
    
    public FactsService() {
        this.httpClient = HttpClient.newHttpClient();
    }
    
    /**
     * Obtenir un fait intéressant depuis l'API
     */
    public String getRandomFact() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return parseFactResponse(response.body());
            } else {
                // Fallback avec fait d'art local
                return getArtFact();
            }
            
        } catch (Exception e) {
            System.err.println("Erreur API Facts: " + e.getMessage());
            // Fallback avec fait d'art local
            return getArtFact();
        }
    }
    
    /**
     * Parser la réponse JSON de l'API
     */
    private String parseFactResponse(String json) {
        try {
            // Parser manuel simple
            if (json.contains("\"text\":")) {
                String fact = json.split("\"text\":\"")[1].split("\"")[0];
                return "🌟 " + fact;
            }
        } catch (Exception e) {
            // Fallback
        }
        return getArtFact();
    }
    
    /**
     * Obtenir un fait sur l'art (fallback local)
     */
    public String getArtFact() {
        return "🎨 " + ART_FACTS[random.nextInt(ART_FACTS.length)];
    }
    
    /**
     * Obtenir un fait formaté pour l'interface
     */
    public String getFormattedFact() {
        return getRandomFact();
    }
    
    /**
     * Vérifier si l'API est accessible
     */
    public boolean isApiAvailable() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .timeout(java.time.Duration.ofSeconds(5))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtenir un fait avec statut de l'API
     */
    public String getFactWithStatus() {
        boolean apiAvailable = isApiAvailable();
        String fact = getRandomFact();
        
        if (apiAvailable) {
            return fact + "\n\n✅ Données en temps réel";
        } else {
            return fact + "\n\n📚 Données locales";
        }
    }
    
    /**
     * Obtenir uniquement des faits sur l'art
     */
    public String getArtFactOnly() {
        return "🎨 " + ART_FACTS[random.nextInt(ART_FACTS.length)];
    }
}
