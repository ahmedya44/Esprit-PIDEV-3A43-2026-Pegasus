package com.pegasus;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;

import java.util.List;
import java.util.Optional;

public class TestArtService {
    
    public static void main(String[] args) {
        ServiceArt serviceArt = new ServiceArt();
        
        System.out.println("=== Test Service Art ===");
        
        // Test 1: Create a new art
        System.out.println("\n1. Création d'un nouvel art...");
        Art newArt = new Art(
            "Tableau Abstrait",
            "Un magnifique tableau abstrait avec des couleurs vives et des formes dynamiques.",
            "https://example.com/images/art1.jpg",
            "available"
        );
        
        boolean created = serviceArt.createArt(newArt);
        if (created) {
            System.out.println("✓ Art créé avec succès! ID: " + newArt.getId());
        } else {
            System.out.println("✗ Échec de la création de l'art");
            return;
        }
        
        // Test 2: Get art by ID
        System.out.println("\n2. Récupération de l'art par ID...");
        Optional<Art> retrievedArt = serviceArt.getArtById(newArt.getId());
        if (retrievedArt.isPresent()) {
            System.out.println("✓ Art trouvé: " + retrievedArt.get());
        } else {
            System.out.println("✗ Art non trouvé");
        }
        
        // Test 3: Update art
        System.out.println("\n3. Mise à jour de l'art...");
        retrievedArt.ifPresent(art -> {
            art.setStatus("sold");
            art.setDescription("Un magnifique tableau abstrait avec des couleurs vives et des formes dynamiques. (VENDU)");
            
            boolean updated = serviceArt.updateArt(art);
            if (updated) {
                System.out.println("✓ Art mis à jour avec succès!");
            } else {
                System.out.println("✗ Échec de la mise à jour");
            }
        });
        
        // Test 4: Create more arts for testing
        System.out.println("\n4. Création d'arts supplémentaires...");
        serviceArt.createArt(new Art("Sculpture Moderne", "Une sculpture en bronze représentant l'harmonie", "https://example.com/images/art2.jpg", "available"));
        serviceArt.createArt(new Art("Photographie Noir & Blanc", "Photo urbaine capturant l'essence de la ville", "https://example.com/images/art3.jpg", "reserved"));
        
        // Test 5: Get all arts
        System.out.println("\n5. Liste de tous les arts...");
        List<Art> allArts = serviceArt.getAllArts();
        System.out.println("Nombre total d'arts: " + allArts.size());
        for (Art art : allArts) {
            System.out.println("- " + art.getTitle() + " (Status: " + art.getStatus() + ")");
        }
        
        // Test 6: Get arts by status
        System.out.println("\n6. Arts disponibles (status: available)...");
        List<Art> availableArts = serviceArt.getArtsByStatus("available");
        System.out.println("Arts disponibles: " + availableArts.size());
        for (Art art : availableArts) {
            System.out.println("- " + art.getTitle());
        }
        
        // Test 7: Delete art (optional - commenté pour éviter la suppression)
        /*
        System.out.println("\n7. Suppression d'un art...");
        boolean deleted = serviceArt.deleteArt(newArt.getId());
        if (deleted) {
            System.out.println("✓ Art supprimé avec succès!");
        } else {
            System.out.println("✗ Échec de la suppression");
        }
        */
        
        System.out.println("\n=== Test terminé ===");
    }
}
