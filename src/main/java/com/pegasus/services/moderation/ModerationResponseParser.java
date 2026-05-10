package com.pegasus.services.moderation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModerationResponseParser {
    private static final double FLAG_THRESHOLD = 0.60;

    public ModerationResult parse(String rawResponse) {
        JsonObject root = JsonParser.parseString(rawResponse).getAsJsonObject();
        List<String> issues = new ArrayList<>();

        double sexualScore = maxOf(
                readPath(root, "sexual", "prob"),
                readPath(root, "sexual", "sexual"),
                readPath(root, "sexual", "explicit")
        );
        if (sexualScore >= FLAG_THRESHOLD) {
            issues.add("Sexual content detected");
        }

        double violenceScore = maxOf(
                readPath(root, "violence", "prob"),
                readPath(root, "violence", "violent"),
                readPath(root, "violence", "weapon")
        );
        if (violenceScore >= FLAG_THRESHOLD) {
            issues.add("Violence detected");
        }

        double hateScore = maxOf(
                readPath(root, "offensive", "discriminatory"),
                readPath(root, "offensive", "hate"),
                readPath(root, "offensive", "racist"),
                readPath(root, "offensive", "sexist")
        );
        if (hateScore >= FLAG_THRESHOLD) {
            issues.add("Hate speech detected");
        }

        double harassmentScore = maxOf(
                readPath(root, "offensive", "insult"),
                readPath(root, "offensive", "toxic"),
                readPath(root, "offensive", "bullying"),
                readPath(root, "offensive", "harassment"),
                readPath(root, "offensive", "profanity")
        );
        if (harassmentScore >= FLAG_THRESHOLD) {
            issues.add("Harassment detected");
        }

        if (issues.isEmpty()) {
            return ModerationResult.safe(rawResponse);
        }
        return ModerationResult.unsafe(issues, rawResponse);
    }

    private double readPath(JsonObject root, String... path) {
        JsonElement current = root;
        for (String segment : path) {
            if (current == null || !current.isJsonObject()) {
                return 0.0;
            }
            current = current.getAsJsonObject().get(segment);
        }
        if (current == null || current.isJsonNull()) {
            return 0.0;
        }
        if (current.isJsonPrimitive() && current.getAsJsonPrimitive().isBoolean()) {
            return current.getAsBoolean() ? 1.0 : 0.0;
        }
        if (current.isJsonPrimitive() && current.getAsJsonPrimitive().isNumber()) {
            return current.getAsDouble();
        }
        if (current.isJsonPrimitive() && current.getAsJsonPrimitive().isString()) {
            String value = current.getAsString().trim().toLowerCase(Locale.ROOT);
            return switch (value) {
                case "true", "yes", "high" -> 1.0;
                default -> 0.0;
            };
        }
        return 0.0;
    }

    private double maxOf(double... values) {
        double max = 0.0;
        for (double value : values) {
            max = Math.max(max, value);
        }
        return max;
    }
}
