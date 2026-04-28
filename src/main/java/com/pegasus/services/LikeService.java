package com.pegasus.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class LikeService {

    private static final String FILE_PATH =
            System.getProperty("user.home") + "/pegasus_likes.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Charger les likes depuis le fichier
    @SuppressWarnings("unchecked")
    private static Map<Integer, Integer> loadLikes() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) return new HashMap<>();
            return mapper.readValue(f, HashMap.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    // Sauvegarder
    private static void saveLikes(Map<Integer, Integer> likes) {
        try {
            mapper.writeValue(new File(FILE_PATH), likes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Toggler un like
    public static boolean toggleLike(int produitId) {
        Map<Integer, Integer> likes = loadLikes();
        if (likes.containsKey(produitId)) {
            likes.remove(produitId);
            saveLikes(likes);
            return false; // unliked
        } else {
            likes.put(produitId, 1);
            saveLikes(likes);
            return true; // liked
        }
    }

    // Savoir si un produit est liké
    public static boolean isLiked(int produitId) {
        return loadLikes().containsKey(produitId);
    }

    // Compter les likes d'un produit
    public static int getLikeCount(int produitId) {
        Map<Integer, Integer> likes = loadLikes();
        return likes.getOrDefault(produitId, 0);
    }
}