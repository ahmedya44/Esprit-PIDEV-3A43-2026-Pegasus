package com.pegasus.entities;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String email;
    private String provider;
    private String googleSub;
    private String roles;
    private String password;
    private String username;
    private String phone;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private String status;
    private String dtype;
    private String resetToken;
    private LocalDateTime resetTokenExpiresAt;
    private String emailVerificationToken;
    private LocalDateTime emailVerificationTokenExpiresAt;

    public User() {

    }

    public User(
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype
    ) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.status = status;
        this.dtype = dtype;
    }

    public User(
            Integer id,
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype
    ) {
        this(
                email,
                roles,
                password,
                username,
                phone,
                avatarUrl,
                createdAt,
                status,
                dtype
        );
        this.id = id;
    }

    public User(int id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.username = joinName(nom, prenom);
        this.email = email;
        this.phone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getGoogleSub() {
        return googleSub;
    }

    public void setGoogleSub(String googleSub) {
        this.googleSub = googleSub;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public String getNom() {
        return username == null ? "" : username;
    }

    public void setNom(String nom) {
        this.username = joinName(nom, getPrenom());
    }

    public String getPrenom() {
        return "";
    }

    public void setPrenom(String prenom) {
        this.username = joinName(getNom(), prenom);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return phone;
    }

    public void setTelephone(String telephone) {
        this.phone = telephone;
    }

    private String joinName(String nom, String prenom) {
        String first = nom == null ? "" : nom.trim();
        String second = prenom == null ? "" : prenom.trim();
        return (first + " " + second).trim();
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

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiresAt() {
        return resetTokenExpiresAt;
    }

    public void setResetTokenExpiresAt(LocalDateTime resetTokenExpiresAt) {
        this.resetTokenExpiresAt = resetTokenExpiresAt;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public LocalDateTime getEmailVerificationTokenExpiresAt() {
        return emailVerificationTokenExpiresAt;
    }

    public void setEmailVerificationTokenExpiresAt(LocalDateTime emailVerificationTokenExpiresAt) {
        this.emailVerificationTokenExpiresAt = emailVerificationTokenExpiresAt;
    }

    @Override
    public String toString() {
        return "User{id=" + id
                + ", email='" + email + "'"
                + ", provider='" + provider + "'"
                + ", googleSub='" + googleSub + "'"
                + ", roles='" + roles + "'"
                + ", username='" + username + "'"
                + ", phone='" + phone + "'"
                + ", avatarUrl='" + avatarUrl + "'"
                + ", createdAt=" + createdAt
                + ", status='" + status + "'"
                + ", dtype='" + dtype + "'"
                + "}";
    }
}
