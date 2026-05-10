package com.pegasus.services.moderation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModerationResult {
    private final boolean safe;
    private final List<String> issues;
    private final String rawResponse;

    private ModerationResult(boolean safe, List<String> issues, String rawResponse) {
        this.safe = safe;
        this.issues = issues;
        this.rawResponse = rawResponse;
    }

    public static ModerationResult safe(String rawResponse) {
        return new ModerationResult(true, Collections.emptyList(), rawResponse);
    }

    public static ModerationResult unsafe(List<String> issues, String rawResponse) {
        return new ModerationResult(false, new ArrayList<>(issues), rawResponse);
    }

    public boolean isSafe() {
        return safe;
    }

    public List<String> getIssues() {
        return issues;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}
