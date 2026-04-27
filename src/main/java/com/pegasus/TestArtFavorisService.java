package com.pegasus;

import com.pegasus.entities.ArtFavoris;
import com.pegasus.services.ServiceArtFavoris;

import java.util.List;

public class TestArtFavorisService {
    
    public static void main(String[] args) {
        ServiceArtFavoris serviceFavoris = new ServiceArtFavoris();
        
        System.out.println("=== Test Service ArtFavoris ===");
        
        // Test data
        String userIdentifier = "user123@example.com";
        int artId1 = 1;
        int artId2 = 2;
        int artId3 = 3;
        
        // Test 1: Add to favoris
        System.out.println("\n1. Ajout aux favoris...");
        boolean added1 = serviceFavoris.addToFavoris(artId1, userIdentifier);
        boolean added2 = serviceFavoris.addToFavoris(artId2, userIdentifier);
        
        if (added1) {
            System.out.println("✓ Art " + artId1 + " ajouté aux favoris");
        } else {
            System.out.println("✗ Échec ajout art " + artId1 + " aux favoris");
        }
        
        if (added2) {
            System.out.println("✓ Art " + artId2 + " ajouté aux favoris");
        } else {
            System.out.println("✗ Échec ajout art " + artId2 + " aux favoris");
        }
        
        // Test 2: Check if in favoris
        System.out.println("\n2. Vérification si dans les favoris...");
        boolean isInFavoris = serviceFavoris.isInFavoris(artId1, userIdentifier);
        System.out.println("Art " + artId1 + " dans les favoris: " + isInFavoris);
        
        // Test 3: Try to add duplicate (should fail)
        System.out.println("\n3. Tentative d'ajout en double...");
        boolean duplicateAdded = serviceFavoris.addToFavoris(artId1, userIdentifier);
        if (!duplicateAdded) {
            System.out.println("✓ Correctement refusé l'ajout en double");
        } else {
            System.out.println("✗ Problème: ajout en double accepté");
        }
        
        // Test 4: Get user favoris
        System.out.println("\n4. Liste des favoris de l'utilisateur...");
        List<ArtFavoris> userFavoris = serviceFavoris.getUserFavoris(userIdentifier);
        System.out.println("Nombre de favoris: " + userFavoris.size());
        for (ArtFavoris favoris : userFavoris) {
            System.out.println("- Art ID: " + favoris.getArtId() + 
                             ", Ajouté le: " + favoris.getAddedAt());
        }
        
        // Test 5: Get favoris count
        System.out.println("\n5. Comptage des favoris...");
        int userCount = serviceFavoris.getUserFavorisCount(userIdentifier);
        System.out.println("Total favoris utilisateur: " + userCount);
        
        int artCount = serviceFavoris.getArtFavorisCount(artId1);
        System.out.println("Total favoris pour art " + artId1 + ": " + artCount);
        
        // Test 6: Toggle favoris (remove since it's already there)
        System.out.println("\n6. Toggle favoris (suppression)...");
        boolean toggled = serviceFavoris.toggleFavoris(artId1, userIdentifier);
        if (toggled) {
            System.out.println("✓ Toggle réussi (art retiré des favoris)");
        } else {
            System.out.println("✗ Échec du toggle");
        }
        
        // Verify it's removed
        boolean stillInFavoris = serviceFavoris.isInFavoris(artId1, userIdentifier);
        System.out.println("Art " + artId1 + " encore dans les favoris: " + stillInFavoris);
        
        // Test 7: Add it back with toggle
        System.out.println("\n7. Toggle favoris (ajout)...");
        boolean toggledBack = serviceFavoris.toggleFavoris(artId1, userIdentifier);
        if (toggledBack) {
            System.out.println("✓ Toggle réussi (art ajouté aux favoris)");
        } else {
            System.out.println("✗ Échec du toggle");
        }
        
        // Test 8: Get art favoris
        System.out.println("\n8. Liste des utilisateurs qui ont favorisé l'art " + artId1 + "...");
        List<ArtFavoris> artFavoris = serviceFavoris.getArtFavoris(artId1);
        System.out.println("Nombre d'utilisateurs: " + artFavoris.size());
        for (ArtFavoris favoris : artFavoris) {
            System.out.println("- Utilisateur: " + favoris.getUserIdentifier() + 
                             ", Ajouté le: " + favoris.getAddedAt());
        }
        
        // Test 9: Remove from favoris
        System.out.println("\n9. Suppression des favoris...");
        boolean removed1 = serviceFavoris.removeFromFavoris(artId1, userIdentifier);
        boolean removed2 = serviceFavoris.removeFromFavoris(artId2, userIdentifier);
        
        if (removed1) {
            System.out.println("✓ Art " + artId1 + " retiré des favoris");
        }
        if (removed2) {
            System.out.println("✓ Art " + artId2 + " retiré des favoris");
        }
        
        // Final count
        int finalCount = serviceFavoris.getUserFavorisCount(userIdentifier);
        System.out.println("Nombre final de favoris: " + finalCount);
        
        System.out.println("\n=== Test terminé ===");
    }
}
