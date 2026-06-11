package com.pegasus.utils;

import com.pegasus.entities.Art;

import java.net.URL;

public final class ArtValidator {

    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MAX_TITLE_LENGTH = 100;
    public static final int MIN_DESCRIPTION_LENGTH = 10;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final int MIN_ARTIST_LENGTH = 2;
    public static final int MAX_ARTIST_LENGTH = 80;
    public static final int MIN_COMMENT_LENGTH = 2;
    public static final int MAX_COMMENT_LENGTH = 500;

    private ArtValidator() {
    }

    public static String validateTitle(String title) {
        String value = title == null ? "" : title.trim();
        if (value.isEmpty()) {
            return "Le titre est obligatoire.";
        }
        if (value.length() < MIN_TITLE_LENGTH) {
            return "Le titre doit contenir au moins " + MIN_TITLE_LENGTH + " caracteres.";
        }
        if (value.length() > MAX_TITLE_LENGTH) {
            return "Le titre ne doit pas depasser " + MAX_TITLE_LENGTH + " caracteres.";
        }
        return null;
    }

    public static String validateDescription(String description) {
        String value = description == null ? "" : description.trim();
        if (value.isEmpty()) {
            return "La description est obligatoire.";
        }
        if (value.length() < MIN_DESCRIPTION_LENGTH) {
            return "La description doit contenir au moins " + MIN_DESCRIPTION_LENGTH + " caracteres.";
        }
        if (value.length() > MAX_DESCRIPTION_LENGTH) {
            return "La description ne doit pas depasser " + MAX_DESCRIPTION_LENGTH + " caracteres.";
        }
        return null;
    }

    public static String validateImageUrl(String imageUrl) {
        String value = imageUrl == null ? "" : imageUrl.trim();
        if (value.isEmpty()) {
            return "L'URL de l'image est obligatoire.";
        }
        if (!isValidHttpUrl(value)) {
            return "Veuillez entrer une URL valide (ex: https://example.com/image.jpg).";
        }
        return null;
    }

    public static String validateArtist(String artist) {
        String value = artist == null ? "" : artist.trim();
        if (value.isEmpty()) {
            return "Le nom de l'artiste est obligatoire.";
        }
        if (value.length() < MIN_ARTIST_LENGTH) {
            return "Le nom de l'artiste doit contenir au moins " + MIN_ARTIST_LENGTH + " caracteres.";
        }
        if (value.length() > MAX_ARTIST_LENGTH) {
            return "Le nom de l'artiste ne doit pas depasser " + MAX_ARTIST_LENGTH + " caracteres.";
        }
        return null;
    }

    public static String validateArtwork(String title, String description, String imageUrl, String artist) {
        String error = validateTitle(title);
        if (error != null) {
            return error;
        }
        error = validateDescription(description);
        if (error != null) {
            return error;
        }
        error = validateImageUrl(imageUrl);
        if (error != null) {
            return error;
        }
        return validateArtist(artist);
    }

    public static String validateArt(Art art) {
        if (art == null) {
            return "Oeuvre invalide.";
        }
        return validateArtwork(art.getTitle(), art.getDescription(), art.getImageUrl(), art.getArtist());
    }

    public static String validateComment(String content) {
        String value = content == null ? "" : content.trim();
        if (value.isEmpty()) {
            return "Le commentaire ne peut pas etre vide.";
        }
        if (value.length() < MIN_COMMENT_LENGTH) {
            return "Le commentaire doit contenir au moins " + MIN_COMMENT_LENGTH + " caracteres.";
        }
        if (value.length() > MAX_COMMENT_LENGTH) {
            return "Le commentaire ne doit pas depasser " + MAX_COMMENT_LENGTH + " caracteres.";
        }
        return null;
    }

    public static boolean isValidHttpUrl(String url) {
        try {
            URL parsed = new URL(url);
            String protocol = parsed.getProtocol();
            return "http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol);
        } catch (Exception e) {
            return false;
        }
    }
}
