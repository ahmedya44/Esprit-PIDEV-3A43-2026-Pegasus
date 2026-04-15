package com.pegasus.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Admin extends User {
    private boolean superAdmin;
    private LocalDate birthDate;

    public Admin() {
    }

    public Admin(
            String email,
            String roles,
            String password,
            String username,
            String phone,
            String avatarUrl,
            LocalDateTime createdAt,
            String status,
            String dtype,
            boolean superAdmin,
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
        this.superAdmin = superAdmin;
        this.birthDate = birthDate;
    }

    public Admin(
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
            boolean superAdmin,
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
                superAdmin,
                birthDate
        );
        setId(id);
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Admin{user=" + super.toString()
                + ", superAdmin=" + superAdmin
                + ", birthDate=" + birthDate
                + "}";
    }
}
