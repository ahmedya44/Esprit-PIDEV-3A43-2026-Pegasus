package com.pegasus.entities;

import java.time.LocalDateTime;

public class Sponsor extends User {
    private String companyName;
    private String website;
    private String address;
    private String description;
    private boolean verified;

    public Sponsor() {
    }

    public Sponsor(
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype,
            String companyName,
            String website,
            String address,
            String description,
            boolean verified
    ) {
        super(email, roles, password, username, phone, avatarUrl, createdAt, status, dtype);
        this.companyName = companyName;
        this.website = website;
        this.address = address;
        this.description = description;
        this.verified = verified;
    }

    public Sponsor(
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
            String companyName,
            String website,
            String address,
            String description,
            boolean verified
    ) {
        this(email, roles, password, username, phone, avatarUrl, createdAt, status, dtype, companyName, website, address, description, verified);
        setId(id);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
