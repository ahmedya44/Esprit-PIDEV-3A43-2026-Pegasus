package com.pegasus.services;

import com.pegasus.dao.CommandeDAO;
import com.pegasus.dao.ProduitDAO;
import com.pegasus.models.Produit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsService {

    public static Map<String, Object> getStatsForProduit(int produitId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("likes", LikeService.getLikeCount(produitId));
        // Tu peux enrichir avec ventes, stock, etc.
        return stats;
    }

    public static Map<Produit, Integer> getLikesParProduit(List<Produit> produits) {
        Map<Produit, Integer> result = new HashMap<>();
        for (Produit p : produits) {
            result.put(p, LikeService.getLikeCount(p.getId()));
        }
        return result;
    }
}