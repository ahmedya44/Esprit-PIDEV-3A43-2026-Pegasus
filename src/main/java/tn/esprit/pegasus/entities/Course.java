package tn.esprit.pegasus.entities;

import java.time.LocalDateTime;

public class Course {
    private int id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String status;
    private LocalDateTime createdAt;
    private int artistId;

    public Course() {
    }

    public Course(int id, String title, String description, String thumbnailUrl,
                  String status, LocalDateTime createdAt, int artistId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.artistId = artistId;
    }

    public Course(String title, String description, String thumbnailUrl,
                  String status, LocalDateTime createdAt, int artistId) {
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.artistId = artistId;
    }

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}