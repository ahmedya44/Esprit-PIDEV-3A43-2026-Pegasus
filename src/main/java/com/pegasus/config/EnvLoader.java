package com.pegasus.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class EnvLoader {
    private static final Map<String, String> DOTENV_VALUES = loadDotEnv();

    private EnvLoader() {
    }

    public static String get(String key) {
        String value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }
        return DOTENV_VALUES.getOrDefault(key, "");
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return value.isBlank() ? defaultValue : value;
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path envPath = Path.of(".env");
        if (!Files.exists(envPath)) {
            return values;
        }

        try {
            for (String line : Files.readAllLines(envPath)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || !trimmed.contains("=")) {
                    continue;
                }

                int separatorIndex = trimmed.indexOf('=');
                String key = trimmed.substring(0, separatorIndex).trim();
                String value = trimmed.substring(separatorIndex + 1).trim();
                values.put(key, stripQuotes(value));
            }
        } catch (IOException ignored) {
        }

        return values;
    }

    private static String stripQuotes(String value) {
        if (value.length() >= 2
                && ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'")))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
