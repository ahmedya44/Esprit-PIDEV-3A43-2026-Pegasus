package com.pegasus.services.moderation;

import com.pegasus.config.EnvLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModerationApiClient {
    private static final String SIGHTENGINE_URL = "https://api.sightengine.com/1.0/text/check.json";
    private static final String SIGHTENGINE_MODELS = "sexual,violence,offensive-2.0";

    private final HttpClient httpClient;

    public ModerationApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(12))
                .build();
    }

    public String analyzeText(String text) throws IOException, InterruptedException {
        String apiUser = EnvLoader.get("SIGHTENGINE_USER");
        String apiSecret = EnvLoader.get("SIGHTENGINE_SECRET");
        if (apiUser.isBlank() || apiSecret.isBlank()) {
            throw new IllegalStateException("Missing Sightengine credentials. Add SIGHTENGINE_USER and SIGHTENGINE_SECRET in .env.");
        }

        Map<String, String> bodyParams = new LinkedHashMap<>();
        bodyParams.put("text", text);
        bodyParams.put("lang", "en");
        bodyParams.put("mode", "rules");
        bodyParams.put("models", SIGHTENGINE_MODELS);
        bodyParams.put("api_user", apiUser);
        bodyParams.put("api_secret", apiSecret);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SIGHTENGINE_URL))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(encodeFormBody(bodyParams)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Sightengine request failed with HTTP " + response.statusCode());
        }
        return response.body();
    }

    private String encodeFormBody(Map<String, String> params) {
        StringBuilder encoded = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!encoded.isEmpty()) {
                encoded.append('&');
            }
            encoded.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            encoded.append('=');
            encoded.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), StandardCharsets.UTF_8));
        }
        return encoded.toString();
    }
}
