package com.pegasus.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pegasus.dao.ProduitDAO;
import com.pegasus.models.Produit;

import java.io.File;
import java.util.*;

public class LikeService {

    private static final String FILE_PATH =
            System.getProperty("user.home") + "/pegasus_likes.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // ── Charger le fichier JSON ────────────────────────────────────────
    private static Map<String, Boolean> loadLikes() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) return new LinkedHashMap<>();
            return mapper.readValue(f,
                    new TypeReference<LinkedHashMap<String, Boolean>>() {});
        } catch (Exception e) {
            return new LinkedHashMap<>();
        }
    }

    // ── Sauvegarder le fichier JSON ────────────────────────────────────
    private static void saveLikes(Map<String, Boolean> likes) {
        try {
            mapper.writeValue(new File(FILE_PATH), likes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Toggle like (retourne true = liké, false = unliké) ─────────────
    public static boolean toggleLike(int produitId) {
        Map<String, Boolean> likes = loadLikes();
        String key = String.valueOf(produitId);
        if (likes.containsKey(key)) {
            likes.remove(key);
            saveLikes(likes);
            return false;
        } else {
            likes.put(key, true);
            saveLikes(likes);
            return true;
        }
    }

    // ── Vérifier si un produit est liké ───────────────────────────────
    public static boolean isLiked(int produitId) {
        return loadLikes().containsKey(String.valueOf(produitId));
    }

    // ── Retourner tous les produits likés ─────────────────────────────
    public static List<Produit> getProduitsLikes() {
        Map<String, Boolean> likes = loadLikes();
        ProduitDAO dao = new ProduitDAO();
        List<Produit> result = new ArrayList<>();
        for (String idStr : likes.keySet()) {
            try {
                Produit p = dao.getById(Integer.parseInt(idStr));
                if (p != null) result.add(p);
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

    // ── Nombre total de likes (pour stats) ────────────────────────────
    public static int getLikeCount(int produitId) {
        return isLiked(produitId) ? 1 : 0;
    }
}