package com.pegasus.forumdesktop.service;

import com.pegasus.config.EnvLoader;

public final class ApiSettings {
    private ApiSettings() {
    }

    public static String value(String name, String fallback) {
        return EnvLoader.getOrDefault(name, fallback);
    }
}
