package com.pegasus.services;

import com.pegasus.entities.Admin;
import com.pegasus.tools.dbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServiceAdmin implements IService<Admin> {
    private final Connection connection;

    public ServiceAdmin() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }

    @Override
    public void ajouter(Admin admin) {
        if (admin.getId() == null) {
            System.err.println("admin id is required (must exist in user table)");
            return;
        }
        String req = "INSERT INTO `admin`(`super_admin`,`id`,`birth_date`) VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setBoolean(1, admin.isSuperAdmin());
            statement.setInt(2, admin.getId());
            setDate(statement, 3, admin.getBirthDate());
            statement.executeUpdate();
            System.out.println("admin added !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Admin admin) {
        if (admin.getId() == null) {
            System.err.println("admin id is required for delete");
            return;
        }
        String req = "DELETE FROM `admin` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, admin.getId());
            statement.executeUpdate();
            System.out.println("admin deleted !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Admin admin) {
        if (admin.getId() == null) {
            System.err.println("admin id is required for update");
            return;
        }
        String req = "UPDATE `admin` SET `super_admin`=?,`birth_date`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setBoolean(1, admin.isSuperAdmin());
            setDate(statement, 2, admin.getBirthDate());
            statement.setInt(3, admin.getId());
            statement.executeUpdate();
            System.out.println("admin updated !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, a.super_admin, a.birth_date FROM `admin` a JOIN `user` u ON a.id = u.id";
        try (Statement st = this.connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Admin admin = mapAdmin(rs);
                System.out.println(admin);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getOneById(int id) {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, a.super_admin, a.birth_date FROM `admin` a JOIN `user` u ON a.id = u.id WHERE a.id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Admin admin = mapAdmin(rs);
                    System.out.println(admin);
                } else {
                    System.out.println("admin not found");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Admin mapAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setEmail(rs.getString("email"));
        admin.setRoles(rs.getString("roles"));
        admin.setPassword(rs.getString("password"));
        admin.setUsername(rs.getString("username"));
        admin.setPhone(rs.getString("phone"));
        admin.setAvatarUrl(rs.getString("avatar_url"));
        admin.setCreatedAt(getDateTime(rs, "created_at"));
        admin.setStatus(rs.getString("status"));
        admin.setDtype(rs.getString("dtype"));
        admin.setResetToken(rs.getString("reset_token"));
        admin.setResetTokenExpiresAt(getDateTime(rs, "reset_token_expires_at"));
        admin.setEmailVerificationToken(rs.getString("email_verification_token"));
        admin.setEmailVerificationTokenExpiresAt(getDateTime(rs, "email_verification_token_expires_at"));
        admin.setSuperAdmin(rs.getBoolean("super_admin"));
        admin.setBirthDate(getDate(rs, "birth_date"));
        return admin;
    }

    private void setDate(PreparedStatement statement, int index, LocalDate value) throws SQLException {
        if (value == null) {
            statement.setDate(index, null);
        } else {
            statement.setDate(index, Date.valueOf(value));
        }
    }

    private LocalDate getDate(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : date.toLocalDate();
    }

    private LocalDateTime getDateTime(ResultSet rs, String column) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(column);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
