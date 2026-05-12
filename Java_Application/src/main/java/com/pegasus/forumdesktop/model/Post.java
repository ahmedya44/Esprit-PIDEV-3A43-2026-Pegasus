package com.pegasus.forumdesktop.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class Post {
    private int id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private Integer ownerId;
    private String ownerName;
    private PostStatus status = PostStatus.IN_PROGRESS;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageName;
    private boolean bannedByAdmin;
    private String requestType = "CREATE";
    private Set<Integer> blacklistedViewerIds = new LinkedHashSet<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName == null || ownerName.isBlank() ? authorName : ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public boolean isOpen() {
        return status == PostStatus.OPEN;
    }

    public boolean isHidden() {
        return false;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isBannedByAdmin() {
        return bannedByAdmin;
    }

    public void setBannedByAdmin(boolean bannedByAdmin) {
        this.bannedByAdmin = bannedByAdmin;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType == null || requestType.isBlank() ? "CREATE" : requestType.trim().toUpperCase();
    }

    public Set<Integer> getBlacklistedViewerIds() {
        return blacklistedViewerIds;
    }

    public void setBlacklistedViewerIds(Set<Integer> blacklistedViewerIds) {
        this.blacklistedViewerIds = blacklistedViewerIds == null ? new LinkedHashSet<>() : blacklistedViewerIds;
    }

    @Override
    public String toString() {
        return "#" + id + " [" + status + "] " + title;
    }
}
