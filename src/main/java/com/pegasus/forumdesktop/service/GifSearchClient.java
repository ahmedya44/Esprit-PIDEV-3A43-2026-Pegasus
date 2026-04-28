package com.pegasus.forumdesktop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pegasus.forumdesktop.model.GifItem;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GifSearchClient {
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String provider = ApiSettings.value("GIF_PROVIDER", "klipy").toLowerCase(Locale.ROOT);
    private final String klipyApiKey = ApiSettings.value("KLIPY_API_KEY", "");
    private final String klipyClientKey = ApiSettings.value("KLIPY_CLIENT_KEY", "pegasus_forum");
    private final String klipyApiUrl = ApiSettings.value("KLIPY_API_URL", "https://api.klipy.com/v2");
    private final String giphyApiKey = ApiSettings.value("GIPHY_API_KEY", "");
    private final String rating = ApiSettings.value("GIPHY_RATING", "pg-13");

    public List<GifItem> search(String query, int limit) {
        int boundedLimit = Math.max(1, Math.min(25, limit));
        if ("giphy".equals(provider) || (klipyApiKey.isBlank() && !giphyApiKey.isBlank())) {
            return searchGiphy(query, boundedLimit);
        }
        return searchKlipy(query, Math.min(20, boundedLimit));
    }

    private List<GifItem> searchKlipy(String query, int limit) {
        if (klipyApiKey.isBlank()) {
            return List.of();
        }
        try {
            String endpoint = query == null || query.isBlank() ? "/featured" : "/search";
            String url = klipyApiUrl.replaceAll("/+$", "") + endpoint
                + "?key=" + encode(klipyApiKey)
                + "&client_key=" + encode(klipyClientKey)
                + "&limit=" + limit
                + "&media_filter=gif,tinygif"
                + "&contentfilter=" + encode(klipyContentFilter());
            if (query != null && !query.isBlank()) {
                url += "&q=" + encode(query);
            }
            JsonNode root = send(url);
            List<GifItem> items = new ArrayList<>();
            for (JsonNode result : root.path("results")) {
                JsonNode formats = result.path("media_formats");
                String urlValue = first(formats.path("gif").path("url").asText(""), formats.path("tinygif").path("url").asText(""));
                if (urlValue.isBlank()) {
                    continue;
                }
                String preview = first(formats.path("tinygif").path("url").asText(""), urlValue);
                String title = first(result.path("content_description").asText(""), result.path("title").asText(""), "GIF");
                items.add(new GifItem(urlValue, preview, title));
            }
            return items;
        } catch (Exception ex) {
            return List.of();
        }
    }

    private List<GifItem> searchGiphy(String query, int limit) {
        if (giphyApiKey.isBlank()) {
            return List.of();
        }
        try {
            String endpoint = query == null || query.isBlank() ? "trending" : "search";
            String url = "https://api.giphy.com/v1/gifs/" + endpoint
                + "?api_key=" + encode(giphyApiKey)
                + "&limit=" + limit
                + "&rating=" + encode(rating);
            if (query != null && !query.isBlank()) {
                url += "&q=" + encode(query);
            }
            JsonNode root = send(url);
            List<GifItem> items = new ArrayList<>();
            for (JsonNode item : root.path("data")) {
                JsonNode images = item.path("images");
                String original = first(images.path("original").path("url").asText(""), item.path("url").asText(""));
                if (original.isBlank()) {
                    continue;
                }
                String preview = first(images.path("fixed_width_small").path("url").asText(""), images.path("preview_gif").path("url").asText(""), original);
                items.add(new GifItem(original, preview, first(item.path("title").asText(""), "GIF")));
            }
            return items;
        } catch (Exception ex) {
            return List.of();
        }
    }

    private JsonNode send(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readTree(response.body());
    }

    private String klipyContentFilter() {
        return switch (rating.toLowerCase(Locale.ROOT).trim()) {
            case "g", "pg" -> "low";
            case "r", "nc-17" -> "high";
            default -> "medium";
        };
    }

    private String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }

    private String first(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return "";
    }
}
