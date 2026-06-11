package com.pegasus.entities;

public class ArtRecommendation {

    private final Art artwork;
    private final String reason;
    private final int matchPercent;

    public ArtRecommendation(Art artwork, String reason, int matchPercent) {
        this.artwork = artwork;
        this.reason = reason;
        this.matchPercent = matchPercent;
    }

    public Art getArtwork() {
        return artwork;
    }

    public String getReason() {
        return reason;
    }

    public int getMatchPercent() {
        return matchPercent;
    }
}
