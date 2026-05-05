package com.pegasus.forumdesktop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AiAutocompleteClient {
    private final ApiHttpClient httpClient = new ApiHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey = ApiSettings.value("GEMINI_API_KEY", "");
    private final String model = ApiSettings.value("GEMINI_MODEL", "gemma-3-4b-it");
    private final String apiUrl = ApiSettings.value("GEMINI_API_URL", "https://generativelanguage.googleapis.com/v1beta/models");

    public List<String> suggest(String field, String text, String context, String locale, int limit) {
        String cleanText = text == null ? "" : text.trim();
        if (cleanText.isBlank()) {
            return List.of();
        }
        if (apiKey.isBlank()) {
            return localFallbackSuggestions(cleanText, limit);
        }
        int boundedLimit = Math.max(1, Math.min(8, limit));
        try {
            ObjectNode payload = mapper.createObjectNode();
            ArrayNode contents = payload.putArray("contents");
            ObjectNode content = contents.addObject();
            ArrayNode parts = content.putArray("parts");
            parts.addObject().put("text", prompt(field, cleanText, context, locale, boundedLimit));
            ObjectNode generation = payload.putObject("generationConfig");
            generation.put("temperature", 0.4);
            generation.put("maxOutputTokens", 140);

            String response = httpClient.postJson(
                buildUrl() + "?key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8),
                mapper.writeValueAsString(payload)
            );
            return normalize(parseRaw(extractText(mapper.readTree(response))), cleanText, boundedLimit);
        } catch (Exception ex) {
            return List.of();
        }
    }

    private String buildUrl() {
        String cleanModel = model.startsWith("models/") ? model.substring("models/".length()) : model;
        return apiUrl.replaceAll("/+$", "") + "/" + cleanModel + ":generateContent";
    }

    private String prompt(String field, String text, String context, String locale, int limit) {
        return """
            You generate short autocomplete continuations for forum content.
            Rules:
            - Keep the same language as the user text (locale hint: %s).
            - Return exactly %d suggestions, one per line.
            - No numbering. No markdown. No JSON.
            - Each line must start with the current text and continue it naturally.
            - Suggestions must be strictly art-related only.

            FIELD: %s
            CURRENT_TEXT: %s
            CONTEXT: %s
            """.formatted(locale, limit, field, text, context == null ? "" : context);
    }

    private String extractText(JsonNode root) {
        StringBuilder builder = new StringBuilder();
        for (JsonNode candidate : root.path("candidates")) {
            for (JsonNode part : candidate.path("content").path("parts")) {
                String text = part.path("text").asText("");
                if (!text.isBlank()) {
                    builder.append(text).append('\n');
                }
            }
        }
        return builder.toString();
    }

    private List<String> parseRaw(String raw) {
        List<String> items = new ArrayList<>();
        for (String line : raw.split("\\R")) {
            String clean = line.replaceFirst("^[-*\\d.)\\s]+", "").trim();
            if (!clean.isBlank()) {
                items.add(clean);
            }
        }
        return items;
    }

    private List<String> normalize(List<String> suggestions, String input, int limit) {
        Set<String> seen = new LinkedHashSet<>();
        List<String> result = new ArrayList<>();
        for (String suggestion : suggestions) {
            String merged = merge(input, suggestion);
            String key = merged.toLowerCase(Locale.ROOT);
            if (!merged.isBlank() && seen.add(key)) {
                result.add(merged);
            }
            if (result.size() >= limit) {
                break;
            }
        }
        return result;
    }

    private List<String> localFallbackSuggestions(String input, int limit) {
        int boundedLimit = Math.max(1, Math.min(8, limit));
        List<String> fallback = new ArrayList<>();
        fallback.add(input + " inspired by surreal art.");
        fallback.add(input + " with warm lighting and textured brushwork.");
        fallback.add(input + " exploring symbolism and emotion.");
        fallback.add(input + " in a modern abstract composition.");
        return normalize(fallback, input, boundedLimit);
    }

    private String merge(String input, String suggestion) {
        if (suggestion.toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT))) {
            return suggestion;
        }
        return input.stripTrailing() + " " + suggestion.stripLeading();
    }
}
