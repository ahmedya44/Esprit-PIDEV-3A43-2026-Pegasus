package com.pegasus.entities;

import java.time.LocalDateTime;

public class ArtView {
    private int id;
    private int artId;
    private String ipAddress;
    private LocalDateTime viewedAt;

    // Constructors
    public ArtView() {
    }

    public ArtView(int artId, String ipAddress) {
        this.artId = artId;
        this.ipAddress = ipAddress;
        this.viewedAt = LocalDateTime.now();
    }

    public ArtView(int id, int artId, String ipAddress, LocalDateTime viewedAt) {
        this.id = id;
        this.artId = artId;
        this.ipAddress = ipAddress;
        this.viewedAt = viewedAt;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    @Override
    public String toString() {
        return "ArtView{" +
                "id=" + id +
                ", artId=" + artId +
                ", ipAddress='" + ipAddress + '\'' +
                ", viewedAt=" + viewedAt +
                '}';
    }
}
