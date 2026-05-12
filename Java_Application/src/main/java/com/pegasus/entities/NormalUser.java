package com.pegasus.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NormalUser extends User {
    private LocalDate birthDate;

    public NormalUser() {
    }

    public NormalUser(
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype,
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
        this.birthDate = birthDate;
    }

    public NormalUser(
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
                birthDate
        );
        setId(id);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "NormalUser{user=" + super.toString()
                + ", birthDate=" + birthDate
                + "}";
    }
}
