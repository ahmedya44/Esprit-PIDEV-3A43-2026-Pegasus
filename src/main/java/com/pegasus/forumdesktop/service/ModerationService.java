package com.pegasus.forumdesktop.service;

import java.util.List;
import java.util.Locale;

public class ModerationService {
    private final List<String> forbiddenWords = List.of("spam", "scam", "insulte", "badword");

    public boolean hasForbiddenWords(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }
        String normalized = text.toLowerCase(Locale.ROOT);
        return forbiddenWords.stream().anyMatch(normalized::contains);
    }
}
