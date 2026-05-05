package tn.esprit.pegasus.services;

import tn.esprit.pegasus.config.EnvLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeVideoSummaryService {
    private static final String YOUTUBE_VIDEO_URL =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&id=%s&key=%s";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    public VideoSummary summarize(String videoUrl) throws IOException, InterruptedException {
        String apiKey = EnvLoader.get("GOOGLE_API_KEY");
        if (apiKey.isBlank()) {
            throw new IOException("Missing GOOGLE_API_KEY in .env.");
        }

        String videoId = extractYouTubeVideoId(videoUrl)
                .orElseThrow(() -> new IOException("This lesson does not contain a valid YouTube link."));

        String requestUrl = String.format(
                YOUTUBE_VIDEO_URL,
                URLEncoder.encode(videoId, StandardCharsets.UTF_8),
                URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("YouTube API error: HTTP " + response.statusCode());
        }

        String body = response.body();
        if (extractString(body, "items").isEmpty() && body.contains("\"items\": []")) {
            throw new IOException("YouTube did not return details for this video.");
        }

        String title = extractString(body, "title").orElse("Untitled video");
        String channel = extractString(body, "channelTitle").orElse("Unknown channel");
        String publishedAt = extractString(body, "publishedAt").map(value -> value.substring(0, Math.min(10, value.length()))).orElse("Unknown date");
        String description = extractString(body, "description").orElse("");
        String duration = extractString(body, "duration").map(this::formatIsoDuration).orElse("Unknown duration");
        String views = extractString(body, "viewCount").orElse("Unknown");

        return new VideoSummary(title, channel, publishedAt, duration, views, buildResume(description));
    }

    public Optional<String> extractYouTubeVideoId(String url) {
        if (url == null || url.isBlank()) {
            return Optional.empty();
        }

        String[] patterns = {
                "(?:youtube\\.com/watch\\?v=)([A-Za-z0-9_-]{11})",
                "(?:youtu\\.be/)([A-Za-z0-9_-]{11})",
                "(?:youtube\\.com/embed/)([A-Za-z0-9_-]{11})",
                "(?:youtube\\.com/shorts/)([A-Za-z0-9_-]{11})"
        };

        for (String pattern : patterns) {
            Matcher matcher = Pattern.compile(pattern).matcher(url.trim());
            if (matcher.find()) {
                return Optional.of(matcher.group(1));
            }
        }
        return Optional.empty();
    }

    private Optional<String> extractString(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:\\\\.|[^\"])*)\"").matcher(json);
        return matcher.find() ? Optional.of(unescapeJson(matcher.group(1))) : Optional.empty();
    }

    private String buildResume(String description) {
        String cleaned = description == null ? "" : description
                .replaceAll("https?://\\S+", "")
                .replaceAll("\\s+", " ")
                .trim();

        if (cleaned.isBlank()) {
            return "No long description is available for this video. Use the title, channel, duration, and course context to decide how it fits the lesson.";
        }

        if (cleaned.length() <= 520) {
            return cleaned;
        }

        return cleaned.substring(0, 520).trim() + "...";
    }

    private String formatIsoDuration(String isoDuration) {
        Matcher matcher = Pattern.compile("PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?").matcher(isoDuration);
        if (!matcher.matches()) {
            return isoDuration;
        }

        int hours = parseDurationPart(matcher.group(1));
        int minutes = parseDurationPart(matcher.group(2));
        int seconds = parseDurationPart(matcher.group(3));

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%d:%02d", minutes, seconds);
    }

    private int parseDurationPart(String value) {
        return value == null || value.isBlank() ? 0 : Integer.parseInt(value);
    }

    private String unescapeJson(String value) {
        return value
                .replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\/", "/")
                .replace("\\\\", "\\");
    }

    public record VideoSummary(
            String title,
            String channel,
            String publishedAt,
            String duration,
            String views,
            String resume
    ) {
    }
}
