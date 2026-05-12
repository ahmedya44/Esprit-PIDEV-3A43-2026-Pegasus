package com.pegasus.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Artiste extends User {
    private String bio;
    private String styles;
    private String facebook;
    private String instagram;
    private String portfolioUrl;
    private boolean verified;
    private LocalDate birthDate;

    public Artiste() {
    }

    public Artiste(
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype,
            String bio,
            String styles,
            String facebook,
            String instagram,
            String portfolioUrl,
            boolean verified,
            LocalDate birthDate
    ) {
        super(
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
        this.bio = bio;
        this.styles = styles;
        this.facebook = facebook;
        this.instagram = instagram;
        this.portfolioUrl = portfolioUrl;
        this.verified = verified;
        this.birthDate = birthDate;
    }

    public Artiste(
            Integer id,
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype,
            String bio,
            String styles,
            String facebook,
            String instagram,
            String portfolioUrl,
            boolean verified,
            LocalDate birthDate
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
                dtype,
                bio,
                styles,
                facebook,
                instagram,
                portfolioUrl,
                verified,
                birthDate
        );
        setId(id);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Artiste{user=" + super.toString()
                + ", bio='" + bio + "'"
                + ", styles='" + styles + "'"
                + ", facebook='" + facebook + "'"
                + ", instagram='" + instagram + "'"
                + ", portfolioUrl='" + portfolioUrl + "'"
                + ", verified=" + verified
                + ", birthDate=" + birthDate
                + "}";
    }
}
