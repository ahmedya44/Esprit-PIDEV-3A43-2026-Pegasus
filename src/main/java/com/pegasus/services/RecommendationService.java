package com.pegasus.services;

import com.pegasus.entities.Art;
import com.pegasus.entities.ArtRecommendation;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationService {

    private static final Set<String> STOP_WORDS = Set.of(
            "le", "la", "les", "de", "du", "des", "un", "une", "et", "en", "au", "aux",
            "pour", "par", "sur", "avec", "ce", "cette", "son", "sa", "ses", "est", "the",
            "and", "or", "of", "in", "to", "for", "art", "oeuvre", "image"
    );

    private final ServiceArt serviceArt;
    private final ServiceArtLike serviceArtLike;

    public RecommendationService() {
        this.serviceArt = new ServiceArt();
        this.serviceArtLike = new ServiceArtLike();
    }

    public List<Art> getSimilarArtworks(int artworkId, int limit) {
        return getRecommendations(artworkId, limit).stream()
                .map(ArtRecommendation::getArtwork)
                .collect(Collectors.toList());
    }

    public List<ArtRecommendation> getRecommendations(int artworkId, int limit) {
        try {
            List<Art> allArtworks = serviceArt.getAllArts();
            Art currentArtwork = allArtworks.stream()
                    .filter(art -> art.getId() == artworkId)
                    .findFirst()
                    .orElse(null);

            if (currentArtwork == null) {
                return List.of();
            }

            Map<Integer, Integer> coLikeCounts = serviceArtLike.getCoLikedArtCounts(artworkId);
            int maxCoLikes = coLikeCounts.values().stream().mapToInt(Integer::intValue).max().orElse(1);
            int maxLikes = allArtworks.stream().mapToInt(Art::getLikes).max().orElse(1);

            List<ScoredRecommendation> scored = new ArrayList<>();
            for (Art candidate : allArtworks) {
                if (candidate.getId() == artworkId || !isPublished(candidate)) {
                    continue;
                }

                double artistScore = artistSimilarity(currentArtwork, candidate);
                double titleScore = textSimilarity(currentArtwork.getTitle(), candidate.getTitle());
                double descriptionScore = textSimilarity(currentArtwork.getDescription(), candidate.getDescription());
                double collaborativeScore = coLikeCounts.getOrDefault(candidate.getId(), 0) / (double) maxCoLikes;
                double popularityScore = candidate.getLikes() / (double) Math.max(1, maxLikes);

                double totalScore = (artistScore * 0.35)
                        + (titleScore * 0.20)
                        + (descriptionScore * 0.20)
                        + (collaborativeScore * 0.15)
                        + (popularityScore * 0.10);

                if (totalScore <= 0.05) {
                    continue;
                }

                scored.add(new ScoredRecommendation(
                        candidate,
                        totalScore,
                        buildReason(artistScore, titleScore, descriptionScore, collaborativeScore, popularityScore)
                ));
            }

            if (scored.isEmpty()) {
                return allArtworks.stream()
                        .filter(art -> art.getId() != artworkId && isPublished(art))
                        .sorted(Comparator.comparingInt(Art::getLikes).reversed())
                        .limit(limit)
                        .map(art -> new ArtRecommendation(art, "Populaire en galerie", 0))
                        .collect(Collectors.toList());
            }

            scored.sort(Comparator.comparingDouble((ScoredRecommendation item) -> item.score).reversed());
            return scored.stream()
                    .limit(limit)
                    .map(item -> new ArtRecommendation(
                            item.artwork,
                            item.reason,
                            Math.min(100, (int) Math.round(item.score * 100))
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error getting recommendations: " + e.getMessage());
            return List.of();
        }
    }

    private boolean isPublished(Art art) {
        if (art.getStatus() == null || art.getStatus().isBlank()) {
            return true;
        }
        String status = art.getStatus().trim().toLowerCase(Locale.ROOT);
        return status.equals("published")
                || status.equals("active")
                || status.equals("approved")
                || status.equals("public");
    }

    private double artistSimilarity(Art source, Art candidate) {
        if (source.getArtist() == null || candidate.getArtist() == null) {
            return 0.0;
        }
        String artist1 = source.getArtist().trim().toLowerCase(Locale.ROOT);
        String artist2 = candidate.getArtist().trim().toLowerCase(Locale.ROOT);
        if (artist1.isEmpty() || artist2.isEmpty()) {
            return 0.0;
        }
        if (artist1.equals(artist2)) {
            return 1.0;
        }
        if (artist1.contains(artist2) || artist2.contains(artist1)) {
            return 0.6;
        }
        return 0.0;
    }

    private double textSimilarity(String left, String right) {
        Set<String> leftTokens = tokenize(left);
        Set<String> rightTokens = tokenize(right);
        if (leftTokens.isEmpty() || rightTokens.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(leftTokens);
        intersection.retainAll(rightTokens);

        Set<String> union = new HashSet<>(leftTokens);
        union.addAll(rightTokens);

        return union.isEmpty() ? 0.0 : intersection.size() / (double) union.size();
    }

    private Set<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(text.toLowerCase(Locale.ROOT).split("[^a-zA-Z0-9àâäéèêëïîôùûüç]+"))
                .map(String::trim)
                .filter(word -> word.length() >= 3)
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toSet());
    }

    private String buildReason(double artistScore, double titleScore, double descriptionScore,
                               double collaborativeScore, double popularityScore) {
        Map<String, Double> reasons = new LinkedHashMap<>();
        reasons.put("Meme artiste", artistScore * 0.35);
        reasons.put("Titre similaire", titleScore * 0.20);
        reasons.put("Theme proche", descriptionScore * 0.20);
        reasons.put("Aime aussi par les utilisateurs", collaborativeScore * 0.15);
        reasons.put("Populaire en galerie", popularityScore * 0.10);

        return reasons.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Suggestion pour vous");
    }

    public List<Art> getPopularArtworks(int limit) {
        try {
            return serviceArt.getAllArts().stream()
                    .filter(this::isPublished)
                    .sorted(Comparator.comparingInt(Art::getLikes).reversed())
                    .limit(limit)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error getting popular artworks: " + e.getMessage());
            return List.of();
        }
    }

    public List<Art> getTrendingArtworks(int limit) {
        return getPopularArtworks(limit);
    }

    private static class ScoredRecommendation {
        private final Art artwork;
        private final double score;
        private final String reason;

        private ScoredRecommendation(Art artwork, double score, String reason) {
            this.artwork = artwork;
            this.score = score;
            this.reason = reason;
        }
    }
}
