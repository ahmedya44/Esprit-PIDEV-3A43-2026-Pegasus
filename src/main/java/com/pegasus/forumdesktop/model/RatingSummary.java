package com.pegasus.forumdesktop.model;

public record RatingSummary(double average, int count) {
    public String label() {
        return count == 0 ? "No ratings yet" : String.format("%.2f / 5 from %d vote(s)", average, count);
    }
}
