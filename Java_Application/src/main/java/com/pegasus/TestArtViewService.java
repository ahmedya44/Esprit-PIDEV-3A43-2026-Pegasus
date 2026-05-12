package com.pegasus;

import com.pegasus.entities.ArtView;
import com.pegasus.services.ServiceArtView;

import java.time.LocalDateTime;
import java.util.List;

public class TestArtViewService {
    
    public static void main(String[] args) {
        ServiceArtView serviceView = new ServiceArtView();
        
        System.out.println("=== Test Service ArtView ===");
        
        // Test data
        String ipAddress1 = "192.168.1.100";
        String ipAddress2 = "10.0.0.50";
        String ipAddress3 = "172.16.0.25";
        int artId1 = 1;
        int artId2 = 2;
        int artId3 = 3;
        
        // Test 1: Record views
        System.out.println("\n1. Enregistrement des vues...");
        boolean viewed1 = serviceView.recordView(artId1, ipAddress1);
        boolean viewed2 = serviceView.recordView(artId2, ipAddress1);
        boolean viewed3 = serviceView.recordView(artId1, ipAddress2);
        boolean viewed4 = serviceView.recordView(artId3, ipAddress3);
        
        if (viewed1) {
            System.out.println("✓ Vue enregistrée pour art " + artId1 + " par IP " + ipAddress1);
        } else {
            System.out.println("✗ Échec enregistrement vue art " + artId1);
        }
        
        if (viewed2) {
            System.out.println("✓ Vue enregistrée pour art " + artId2 + " par IP " + ipAddress1);
        } else {
            System.out.println("✗ Échec enregistrement vue art " + artId2);
        }
        
        if (viewed3) {
            System.out.println("✓ Vue enregistrée pour art " + artId1 + " par IP " + ipAddress2);
        } else {
            System.out.println("✗ Échec enregistrement vue art " + artId1);
        }
        
        if (viewed4) {
            System.out.println("✓ Vue enregistrée pour art " + artId3 + " par IP " + ipAddress3);
        } else {
            System.out.println("✗ Échec enregistrement vue art " + artId3);
        }
        
        // Test 2: Get art views
        System.out.println("\n2. Liste des vues d'un art...");
        List<ArtView> artViews = serviceView.getArtViews(artId1);
        System.out.println("Nombre de vues pour art " + artId1 + ": " + artViews.size());
        for (ArtView view : artViews) {
            System.out.println("- IP: " + view.getIpAddress() + 
                             ", Vu le: " + view.getViewedAt());
        }
        
        // Test 3: Get IP views
        System.out.println("\n3. Liste des vues d'une IP...");
        List<ArtView> ipViews = serviceView.getIpViews(ipAddress1);
        System.out.println("Nombre de vues pour IP " + ipAddress1 + ": " + ipViews.size());
        for (ArtView view : ipViews) {
            System.out.println("- Art ID: " + view.getArtId() + 
                             ", Vu le: " + view.getViewedAt());
        }
        
        // Test 4: Get view counts
        System.out.println("\n4. Comptage des vues...");
        int artCount = serviceView.getArtViewCount(artId1);
        int ipCount = serviceView.getIpViewCount(ipAddress1);
        int totalCount = serviceView.getTotalViews();
        int uniqueCount = serviceView.getUniqueViewCount(artId1);
        
        System.out.println("Total vues art " + artId1 + ": " + artCount);
        System.out.println("Total vues IP " + ipAddress1 + ": " + ipCount);
        System.out.println("Total vues toutes œuvres: " + totalCount);
        System.out.println("Vues uniques art " + artId1 + ": " + uniqueCount);
        
        // Test 5: Check recent views
        System.out.println("\n5. Vérification des vues récentes...");
        boolean hasViewedRecently = serviceView.hasViewedRecently(artId1, ipAddress1, 5);
        System.out.println("IP " + ipAddress1 + " a vu art " + artId1 + " récemment (< 5 min): " + hasViewedRecently);
        
        // Test 6: Get most viewed arts
        System.out.println("\n6. Œuvres les plus vues...");
        List<Integer> mostViewed = serviceView.getMostViewedArts(5);
        System.out.println("Top " + mostViewed.size() + " œuvres les plus vues:");
        for (int i = 0; i < mostViewed.size(); i++) {
            System.out.println((i + 1) + ". Art ID: " + mostViewed.get(i));
        }
        
        // Test 7: Get recent views
        System.out.println("\n7. Vues les plus récentes...");
        List<ArtView> recentViews = serviceView.getRecentViews(3);
        System.out.println("3 dernières vues:");
        for (ArtView view : recentViews) {
            System.out.println("- Art ID: " + view.getArtId() + 
                             ", IP: " + view.getIpAddress() + 
                             ", Vu le: " + view.getViewedAt());
        }
        
        // Test 8: Get views by date range
        System.out.println("\n8. Vues par plage de dates...");
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now();
        List<ArtView> rangeViews = serviceView.getViewsByDateRange(startDate, endDate);
        System.out.println("Vues entre " + startDate + " et " + endDate + ": " + rangeViews.size());
        
        // Test 9: Cleanup old views (optional)
        System.out.println("\n9. Nettoyage des anciennes vues...");
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        serviceView.cleanupOldViews(cutoffDate);
        
        System.out.println("\n=== Test terminé ===");
    }
}
