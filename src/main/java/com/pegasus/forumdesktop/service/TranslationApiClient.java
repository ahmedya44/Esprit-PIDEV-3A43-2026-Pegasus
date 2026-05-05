package com.pegasus.forumdesktop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class TranslationApiClient {
    private static final List<String> KNOWN_LOCALES = List.of("fr", "en", "es", "de", "it", "ar");
    private final ApiHttpClient httpClient = new ApiHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String translate(String text, String targetLocale, String sourceLocale) {
        String cleanText = text == null ? "" : text.trim();
        if (cleanText.isBlank()) {
            return "";
        }

        String target = normalizeLocale(targetLocale);
        if (target == null) {
            target = "en";
        }
        String source = detectSourceLocale(cleanText, sourceLocale);
        if (sameLanguage(source, target)) {
            return cleanText;
        }

        for (String candidate : sourceCandidates(source)) {
            if (sameLanguage(candidate, target)) {
                continue;
            }
            String translated = requestTranslation(cleanText, candidate, target);
            if (translated != null && !translated.isBlank()) {
                return translated;
            }
        }
        throw new IllegalStateException("Translation API did not return a usable translation.");
    }

    private String requestTranslation(String text, String sourceLocale, String targetLocale) {
        String translated = requestMyMemoryTranslation(text, sourceLocale, targetLocale);
        if (translated != null && !translated.isBlank()) {
            return translated;
        }
        return requestGoogleTranslation(text, sourceLocale, targetLocale);
    }

    private String requestMyMemoryTranslation(String text, String sourceLocale, String targetLocale) {
        try {
            String query = "q=" + encode(text) + "&langpair=" + encode(sourceLocale + "|" + targetLocale);
            String response = httpClient.get("https://api.mymemory.translated.net/get?" + query);
            JsonNode root = mapper.readTree(response);
            String details = root.path("responseDetails").asText("");
            if (details.toLowerCase(Locale.ROOT).contains("invalid source language")) {
                return null;
            }
            String translated = root.path("responseData").path("translatedText").asText("");
            if (translated.toLowerCase(Locale.ROOT).contains("please select two distinct languages")) {
                return null;
            }
            return translated.trim();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            return null;
        }
    }

    private String requestGoogleTranslation(String text, String sourceLocale, String targetLocale) {
        try {
            String query = "client=gtx"
                + "&sl=" + encode(sourceLocale)
                + "&tl=" + encode(targetLocale)
                + "&dt=t"
                + "&q=" + encode(text);
            String response = httpClient.get("https://translate.googleapis.com/translate_a/single?" + query);
            JsonNode root = mapper.readTree(response);
            StringBuilder translated = new StringBuilder();
            for (JsonNode sentence : root.path(0)) {
                String part = sentence.path(0).asText("");
                if (!part.isBlank()) {
                    translated.append(part);
                }
            }
            return translated.toString().trim();
        } catch (Exception ex) {
            return null;
        }
    }

    private List<String> sourceCandidates(String sourceLocale) {
        java.util.ArrayList<String> locales = new java.util.ArrayList<>();
        locales.add(sourceLocale);
        for (String locale : KNOWN_LOCALES) {
            if (!locales.contains(locale)) {
                locales.add(locale);
            }
        }
        return locales;
    }

    private String detectSourceLocale(String text, String sourceLocale) {
        String hint = normalizeLocale(sourceLocale);
        if (hint != null) {
            return hint;
        }
        if (text.matches(".*[\\u0600-\\u06FF].*")) {
            return "ar";
        }
        String sample = " " + text.toLowerCase(Locale.ROOT).replaceAll("<[^>]+>", "") + " ";
        java.util.Map<String, List<String>> terms = java.util.Map.of(
            "fr", List.of(" le ", " la ", " les ", " des ", " une ", " est ", " et ", " pour "),
            "en", List.of(" the ", " and ", " is ", " are ", " this ", " with ", " for "),
            "es", List.of(" el ", " la ", " los ", " que ", " de ", " y ", " para "),
            "de", List.of(" der ", " die ", " das ", " und ", " ist ", " mit "),
            "it", List.of(" il ", " la ", " che ", " e ", " per ", " con ")
        );
        String best = "en";
        int bestScore = 0;
        for (var entry : terms.entrySet()) {
            int score = entry.getValue().stream().mapToInt(term -> sample.split(java.util.regex.Pattern.quote(term), -1).length - 1).sum();
            if (score > bestScore) {
                bestScore = score;
                best = entry.getKey();
            }
        }
        return best;
    }

    private String normalizeLocale(String locale) {
        if (locale == null || locale.isBlank() || locale.equalsIgnoreCase("auto") || locale.equalsIgnoreCase("orig")) {
            return null;
        }
        String lower = locale.trim().toLowerCase(Locale.ROOT);
        return lower.matches("[a-z]{2}(-[a-z]{2})?") ? lower : null;
    }

    private boolean sameLanguage(String a, String b) {
        return a.split("-")[0].equalsIgnoreCase(b.split("-")[0]);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
