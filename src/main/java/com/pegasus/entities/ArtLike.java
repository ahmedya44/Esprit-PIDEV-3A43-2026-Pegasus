package com.pegasus.entities;

import java.time.LocalDateTime;

public class ArtLike {
    private int id;
    private int artId;
    private String sessionId;
    private LocalDateTime createdAt;

    // Constructors
    public ArtLike() {
    }

    public ArtLike(int artId, String sessionId) {
        this.artId = artId;
        this.sessionId = sessionId;
        this.createdAt = LocalDateTime.now();
    }

    public ArtLike(int id, int artId, String sessionId, LocalDateTime createdAt) {
        this.id = id;
        this.artId = artId;
        this.sessionId = sessionId;
        this.createdAt = createdAt;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ArtLike{" +
                "id=" + id +
                ", artId=" + artId +
                ", sessionId='" + sessionId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
