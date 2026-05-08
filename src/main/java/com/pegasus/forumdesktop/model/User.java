package com.pegasus.forumdesktop.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String email;
    private String rolesJson;
    private String password;
    private String username;
    private String phone;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private String status;
    private String dtype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolesJson() {
        return rolesJson;
    }

    public void setRolesJson(String rolesJson) {
        this.rolesJson = rolesJson;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public boolean isAdmin() {
        return rolesJson != null && rolesJson.contains("ROLE_ADMIN");
    }

    public String getDisplayName() {
        return username == null || username.isBlank() ? email : username;
    }

    @Override
    public String toString() {
        return "#" + id + " " + getDisplayName() + " <" + email + ">";
    }
}
