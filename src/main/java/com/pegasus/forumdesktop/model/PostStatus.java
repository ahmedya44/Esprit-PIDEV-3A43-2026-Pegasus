package com.pegasus.forumdesktop.model;

public enum PostStatus {
    OPEN,
    CLOSED,
    HIDDEN;

    public static PostStatus fromDatabase(String value) {
        if (value == null || value.isBlank()) {
            return OPEN;
        }
        return PostStatus.valueOf(value.trim().toUpperCase());
    }
}
