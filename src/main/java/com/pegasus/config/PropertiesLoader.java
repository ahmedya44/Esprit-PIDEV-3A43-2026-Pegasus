package com.pegasus.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PropertiesLoader {
    private PropertiesLoader() {
    }

    public static Properties load(String filename, Class<?> resourceOwner) {
        Properties properties = new Properties();
        loadFromWorkingDirectory(properties, filename);
        loadFromClasspath(properties, filename, resourceOwner);
        return properties;
    }

    private static void loadFromWorkingDirectory(Properties properties, String filename) {
        Path path = Path.of(normalizeWorkingDirectoryFilename(filename));
        if (!Files.isRegularFile(path)) {
            return;
        }
        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        } catch (IOException ignored) {
        }
    }

    private static void loadFromClasspath(Properties properties, String filename, Class<?> resourceOwner) {
        String resourcePath = filename.startsWith("/") ? filename : "/" + filename;
        Class<?> owner = resourceOwner == null ? PropertiesLoader.class : resourceOwner;
        try (InputStream inputStream = owner.getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException ignored) {
        }
    }

    private static String normalizeWorkingDirectoryFilename(String filename) {
        while (filename.startsWith("/") || filename.startsWith("\\")) {
            filename = filename.substring(1);
        }
        return filename;
    }
}
