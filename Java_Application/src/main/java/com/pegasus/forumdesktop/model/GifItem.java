package com.pegasus.forumdesktop.model;

public record GifItem(String url, String preview, String title) {
    @Override
    public String toString() {
        return title == null || title.isBlank() ? url : title + " - " + url;
    }
}
