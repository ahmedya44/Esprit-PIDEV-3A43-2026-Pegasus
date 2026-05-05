package com.pegasus.entities;

import java.time.LocalDateTime;

public class ArtFavoris {
    private int id;
    private int artId;
    private String userIdentifier;
    private LocalDateTime addedAt;

    // Constructors
    public ArtFavoris() {
    }

    public ArtFavoris(int artId, String userIdentifier) {
        this.artId = artId;
        this.userIdentifier = userIdentifier;
        this.addedAt = LocalDateTime.now();
    }

    public ArtFavoris(int id, int artId, String userIdentifier, LocalDateTime addedAt) {
        this.id = id;
        this.artId = artId;
        this.userIdentifier = userIdentifier;
        this.addedAt = addedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtId() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "ArtFavoris{" +
                "id=" + id +
                ", artId=" + artId +
                ", userIdentifier='" + userIdentifier + '\'' +
                ", addedAt=" + addedAt +
                '}';
    }
}
