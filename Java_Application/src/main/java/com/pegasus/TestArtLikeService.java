package com.pegasus;

import com.pegasus.entities.ArtLike;
import com.pegasus.services.ServiceArtLike;

import java.time.LocalDateTime;
import java.util.List;

public class TestArtLikeService {
    
    public static void main(String[] args) {
        ServiceArtLike serviceLike = new ServiceArtLike();
        
        System.out.println("=== Test Service ArtLike ===");
        
        // Test data
        String sessionId1 = "session_abc123";
        String sessionId2 = "session_xyz789";
        int artId1 = 1;
        int artId2 = 2;
        int artId3 = 3;
        
        // Test 1: Add likes
        System.out.println("\n1. Ajout de likes...");
        boolean liked1 = serviceLike.addLike(artId1, sessionId1);
        boolean liked2 = serviceLike.addLike(artId2, sessionId1);
        boolean liked3 = serviceLike.addLike(artId1, sessionId2);
        
        if (liked1) {
            System.out.println("✓ Art " + artId1 + " liké par session " + sessionId1);
        } else {
            System.out.println("✗ Échec du like art " + artId1);
        }
        
        if (liked2) {
            System.out.println("✓ Art " + artId2 + " liké par session " + sessionId1);
        } else {
            System.out.println("✗ Échec du like art " + artId2);
        }
        
        if (liked3) {
            System.out.println("✓ Art " + artId1 + " liké par session " + sessionId2);
        } else {
            System.out.println("✗ Échec du like art " + artId1);
        }
        
        // Test 2: Check if liked
        System.out.println("\n2. Vérification des likes...");
        boolean hasLiked1 = serviceLike.hasLiked(artId1, sessionId1);
        boolean hasLiked2 = serviceLike.hasLiked(artId3, sessionId1);
        System.out.println("Session " + sessionId1 + " a liké art " + artId1 + ": " + hasLiked1);
        System.out.println("Session " + sessionId1 + " a liké art " + artId3 + ": " + hasLiked2);
        
        // Test 3: Try to add duplicate (should fail)
        System.out.println("\n3. Tentative de like en double...");
        boolean duplicateLiked = serviceLike.addLike(artId1, sessionId1);
        if (!duplicateLiked) {
            System.out.println("✓ Correctement refusé le like en double");
        } else {
            System.out.println("✗ Problème: like en double accepté");
        }
        
        // Test 4: Get session likes
        System.out.println("\n4. Liste des likes de la session...");
        List<ArtLike> sessionLikes = serviceLike.getSessionLikes(sessionId1);
        System.out.println("Nombre de likes pour session " + sessionId1 + ": " + sessionLikes.size());
        for (ArtLike like : sessionLikes) {
            System.out.println("- Art ID: " + like.getArtId() + 
                             ", Liké le: " + like.getCreatedAt());
        }
        
        // Test 5: Get art likes
        System.out.println("\n5. Liste des sessions qui ont liké l'art " + artId1 + "...");
        List<ArtLike> artLikes = serviceLike.getArtLikes(artId1);
        System.out.println("Nombre de likes pour art " + artId1 + ": " + artLikes.size());
        for (ArtLike like : artLikes) {
            System.out.println("- Session: " + like.getSessionId() + 
                             ", Liké le: " + like.getCreatedAt());
        }
        
        // Test 6: Get like counts
        System.out.println("\n6. Comptage des likes...");
        int sessionCount = serviceLike.getSessionLikeCount(sessionId1);
        int artCount = serviceLike.getArtLikeCount(artId1);
        System.out.println("Total likes session " + sessionId1 + ": " + sessionCount);
        System.out.println("Total likes art " + artId1 + ": " + artCount);
        
        // Test 7: Toggle like (remove since it's already there)
        System.out.println("\n7. Toggle like (suppression)...");
        boolean toggled = serviceLike.toggleLike(artId1, sessionId1);
        if (toggled) {
            System.out.println("✓ Toggle réussi (like retiré)");
        } else {
            System.out.println("✗ Échec du toggle");
        }
        
        // Verify it's removed
        boolean stillLiked = serviceLike.hasLiked(artId1, sessionId1);
        System.out.println("Art " + artId1 + " encore liké par session " + sessionId1 + ": " + stillLiked);
        
        // Test 8: Add it back with toggle
        System.out.println("\n8. Toggle like (ajout)...");
        boolean toggledBack = serviceLike.toggleLike(artId1, sessionId1);
        if (toggledBack) {
            System.out.println("✓ Toggle réussi (like ajouté)");
        } else {
            System.out.println("✗ Échec du toggle");
        }
        
        // Test 9: Get most liked arts
        System.out.println("\n9. Arts les plus likés...");
        List<Integer> mostLiked = serviceLike.getMostLikedArts(5);
        System.out.println("Top " + mostLiked.size() + " arts les plus likés:");
        for (int i = 0; i < mostLiked.size(); i++) {
            System.out.println((i + 1) + ". Art ID: " + mostLiked.get(i));
        }
        
        // Test 10: Remove likes
        System.out.println("\n10. Suppression des likes...");
        boolean removed1 = serviceLike.removeLike(artId1, sessionId1);
        boolean removed2 = serviceLike.removeLike(artId2, sessionId1);
        boolean removed3 = serviceLike.removeLike(artId1, sessionId2);
        
        if (removed1) {
            System.out.println("✓ Like retiré pour art " + artId1 + " session " + sessionId1);
        }
        if (removed2) {
            System.out.println("✓ Like retiré pour art " + artId2 + " session " + sessionId1);
        }
        if (removed3) {
            System.out.println("✓ Like retiré pour art " + artId1 + " session " + sessionId2);
        }
        
        // Final counts
        int finalSessionCount = serviceLike.getSessionLikeCount(sessionId1);
        int finalArtCount = serviceLike.getArtLikeCount(artId1);
        System.out.println("Nombre final de likes session " + sessionId1 + ": " + finalSessionCount);
        System.out.println("Nombre final de likes art " + artId1 + ": " + finalArtCount);
        
        // Test 11: Cleanup old likes (optional)
        System.out.println("\n11. Nettoyage des anciens likes...");
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        serviceLike.cleanupOldLikes(cutoffDate);
        
        System.out.println("\n=== Test terminé ===");
    }
}
