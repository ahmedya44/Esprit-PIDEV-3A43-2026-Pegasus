package com.pegasus.services;

import com.pegasus.config.EnvLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FantasyChatbotService {
    private static final String CLOUDFLARE_TOKEN_PLACEHOLDER = "put_your_cloudflare_api_token_here";
    private static final String CLOUDFLARE_IMAGE_URL_TEMPLATE =
            "https://api.cloudflare.com/client/v4/accounts/%s/ai/run/%s";
    private static final Pattern IMAGE_PATTERN = Pattern.compile("\"image\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern RESULT_PATTERN = Pattern.compile("\"result\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern ERROR_MESSAGE_PATTERN = Pattern.compile("\"message\"\\s*:\\s*\"([^\"]+)\"");

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public ChatbotResponse createFantasyImage(String userIdea) throws IOException, InterruptedException {
        String polishedPrompt = buildFantasyPrompt(userIdea);
        String accountId = EnvLoader.get("CLOUDFLARE_ACCOUNT_ID");
        String apiToken = EnvLoader.get("CLOUDFLARE_API_TOKEN");
        if (accountId.isBlank() || apiToken.isBlank() || CLOUDFLARE_TOKEN_PLACEHOLDER.equals(apiToken)) {
            return ChatbotResponse.promptOnly(
                    "Add your Cloudflare account id and API token in the .env file, then I can generate the image. Prompt ready: "
                            + polishedPrompt
            );
        }

        String requestBody = buildImageRequestBody(polishedPrompt);
        String model = EnvLoader.getOrDefault("CLOUDFLARE_AI_MODEL", "@cf/leonardo/lucid-origin");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(CLOUDFLARE_IMAGE_URL_TEMPLATE, accountId, model)))
                .timeout(Duration.ofSeconds(90))
                .header("Authorization", "Bearer " + apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            String body = new String(response.body());
            String message = extractErrorMessage(body).orElse("Image generation failed.");
            return ChatbotResponse.promptOnly("Cloudflare API error: " + message);
        }

        Optional<String> imageBase64 = extractImageBase64(response);
        if (imageBase64.isEmpty()) {
            return ChatbotResponse.promptOnly("Cloudflare answered, but I could not find image data in the response.");
        }

        return ChatbotResponse.withImage("Done. I generated this from: " + polishedPrompt, imageBase64.get());
    }

    private String buildImageRequestBody(String prompt) {
        String width = EnvLoader.getOrDefault("CLOUDFLARE_IMAGE_WIDTH", "1024");
        String height = EnvLoader.getOrDefault("CLOUDFLARE_IMAGE_HEIGHT", "1024");
        String steps = EnvLoader.getOrDefault("CLOUDFLARE_IMAGE_STEPS", "20");
        String guidance = EnvLoader.getOrDefault("CLOUDFLARE_IMAGE_GUIDANCE", "7.5");

        return "{"
                + "\"prompt\":\"" + escapeJson(prompt) + "\","
                + "\"width\":" + numberOrDefault(width, "1024") + ","
                + "\"height\":" + numberOrDefault(height, "1024") + ","
                + "\"steps\":" + numberOrDefault(steps, "20") + ","
                + "\"guidance\":" + numberOrDefault(guidance, "7.5")
                + "}";
    }

    private String buildFantasyPrompt(String userIdea) {
        return userIdea
                + ", art and fantasy theme, polished fantasy concept art, cinematic lighting, rich textures, "
                + "magical atmosphere, elegant composition, highly detailed, no text, no watermark";
    }

    private Optional<String> extractImageBase64(HttpResponse<byte[]> response) {
        String contentType = response.headers().firstValue("content-type").orElse("");
        if (contentType.startsWith("image/") || looksLikeBinaryImage(response.body())) {
            return Optional.of(java.util.Base64.getEncoder().encodeToString(response.body()));
        }

        String responseBody = new String(response.body());
        Matcher imageMatcher = IMAGE_PATTERN.matcher(responseBody);
        if (imageMatcher.find()) {
            return Optional.of(imageMatcher.group(1));
        }

        Matcher resultMatcher = RESULT_PATTERN.matcher(responseBody);
        return resultMatcher.find() ? Optional.of(resultMatcher.group(1)) : Optional.empty();
    }

    private boolean looksLikeBinaryImage(byte[] bytes) {
        boolean isJpeg = bytes.length > 2
                && (bytes[0] & 0xff) == 0xff
                && (bytes[1] & 0xff) == 0xd8;
        boolean isPng = bytes.length > 8
                && (bytes[0] & 0xff) == 0x89
                && bytes[1] == 0x50
                && bytes[2] == 0x4e
                && bytes[3] == 0x47;
        return isJpeg || isPng;
    }

    private Optional<String> extractErrorMessage(String responseBody) {
        Matcher matcher = ERROR_MESSAGE_PATTERN.matcher(responseBody);
        return matcher.find() ? Optional.of(unescapeJson(matcher.group(1))) : Optional.empty();
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String numberOrDefault(String value, String defaultValue) {
        try {
            Double.parseDouble(value);
            return value;
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    private String unescapeJson(String value) {
        return value
                .replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }

    public static class ChatbotResponse {
        private final String message;
        private final String imageBase64;

        private ChatbotResponse(String message, String imageBase64) {
            this.message = message;
            this.imageBase64 = imageBase64;
        }

        public static ChatbotResponse promptOnly(String message) {
            return new ChatbotResponse(message, null);
        }

        public static ChatbotResponse withImage(String message, String imageBase64) {
            return new ChatbotResponse(message, imageBase64);
        }

        public String getMessage() {
            return message;
        }

        public Optional<String> getImageBase64() {
            return Optional.ofNullable(imageBase64);
        }
    }
}
