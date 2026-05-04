package com.pegasus.entities;

import java.time.LocalDateTime;

public class Art {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String status;
    private String artist;
    private LocalDateTime createdAt;
    private int likes;
    private int dislikes;

    // Constructors
    public Art() {
    }

    public Art(String title, String description, String imageUrl, String status) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.likes = 0;
        this.dislikes = 0;
    }

    public Art(int id, String title, String description, String imageUrl, String status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.likes = 0;
        this.dislikes = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Art{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
