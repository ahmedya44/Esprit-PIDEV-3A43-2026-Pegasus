package com.pegasus.forumdesktop.model;

public enum PostStatus {
    IN_PROGRESS,
    OPEN,
    CLOSED,
    DENIED;

    public static PostStatus fromDatabase(String value) {
        if (value == null || value.isBlank()) {
            return IN_PROGRESS;
        }
        try {
            return PostStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return IN_PROGRESS;
        }
    }
}
