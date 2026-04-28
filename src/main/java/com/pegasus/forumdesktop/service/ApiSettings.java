package com.pegasus.forumdesktop.service;

public final class ApiSettings {
    private ApiSettings() {
    }

    public static String value(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
