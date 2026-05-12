package com.pegasus.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pegasus.config.EnvLoader;
import com.pegasus.config.PropertiesLoader;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class CloudinaryService {
    private static final String CONFIG_PATH = "/cloudinary.properties";
    private static final String DEFAULT_FOLDER = "pegasus/profilePics";

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        Properties properties = loadProperties();
        String cloudName = readValue(properties, "cloudinary.cloudName", "CLOUDINARY_CLOUD_NAME");
        String apiKey = readValue(properties, "cloudinary.apiKey", "CLOUDINARY_API_KEY");
        String apiSecret = readValue(properties, "cloudinary.apiSecret", "CLOUDINARY_API_SECRET");

        if (cloudName == null || apiKey == null || apiSecret == null) {
            throw new IllegalStateException("Cloudinary config is incomplete. Set cloudinary properties or env vars.");
        }
        if (cloudName.startsWith("YOUR_") || apiKey.startsWith("YOUR_") || apiSecret.startsWith("YOUR_")) {
            throw new IllegalStateException("Replace placeholder values in cloudinary.properties.");
        }

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    public String uploadProfileImage(File file, Integer userId) {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Profile image file is invalid.");
        }
        String publicId = "user_" + (userId == null ? "x" : userId) + "_" + UUID.randomUUID().toString().replace("-", "");

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary.uploader().upload(
                    file,
                    ObjectUtils.asMap(
                            "folder", DEFAULT_FOLDER,
                            "public_id", publicId,
                            "resource_type", "image",
                            "overwrite", true
                    )
            );

            Object secureUrl = result.get("secure_url");
            if (secureUrl != null && !secureUrl.toString().isBlank()) {
                return secureUrl.toString();
            }
            Object url = result.get("url");
            if (url != null && !url.toString().isBlank()) {
                return url.toString();
            }
            throw new IllegalStateException("Cloudinary upload succeeded but returned no URL.");
        } catch (Exception e) {
            throw new IllegalStateException("Cloudinary upload failed: " + e.getMessage(), e);
        }
    }

    private Properties loadProperties() {
        return PropertiesLoader.load(CONFIG_PATH, CloudinaryService.class);
    }

    private String readValue(Properties properties, String propertyKey, String envKey) {
        String value = trimToNull(properties.getProperty(propertyKey));
        if (value != null) {
            return value;
        }
        return trimToNull(EnvLoader.get(envKey));
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

}
