package com.pegasus.services;

import com.pegasus.entities.NormalUser;
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

public class ServiceNormalUser implements IService<NormalUser> {
    private final Connection connection;
    private String lastError;

    public ServiceNormalUser() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(dbConnection.buildConnectionErrorMessage(e), e);
        }
    }

    @Override
    public void ajouter(NormalUser normalUser) {
        lastError = null;
        if (normalUser.getId() == null) {
            lastError = "normal_user id is required (must exist in user table)";
            System.err.println(lastError);
            return;
        }
        String req = "INSERT INTO `normal_user`(`birth_date`,`id`) VALUES (?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            setDate(statement, 1, normalUser.getBirthDate());
            statement.setInt(2, normalUser.getId());
            statement.executeUpdate();
            System.out.println("normal_user added !");
        } catch (SQLException e) {
            lastError = e.getMessage();
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(NormalUser normalUser) {
        if (normalUser.getId() == null) {
            System.err.println("normal_user id is required for delete");
            return;
        }
        String req = "DELETE FROM `normal_user` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, normalUser.getId());
            statement.executeUpdate();
            System.out.println("normal_user deleted !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(NormalUser normalUser) {
        if (normalUser.getId() == null) {
            System.err.println("normal_user id is required for update");
            return;
        }
        String req = "UPDATE `normal_user` SET `birth_date`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            setDate(statement, 1, normalUser.getBirthDate());
            statement.setInt(2, normalUser.getId());
            statement.executeUpdate();
            System.out.println("normal_user updated !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, n.birth_date FROM `normal_user` n JOIN `user` u ON n.id = u.id";
        try (Statement st = this.connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                NormalUser normalUser = mapNormalUser(rs);
                System.out.println(normalUser);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getOneById(int id) {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, n.birth_date FROM `normal_user` n JOIN `user` u ON n.id = u.id WHERE n.id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    NormalUser normalUser = mapNormalUser(rs);
                    System.out.println(normalUser);
                } else {
                    System.out.println("normal_user not found");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private NormalUser mapNormalUser(ResultSet rs) throws SQLException {
        NormalUser normalUser = new NormalUser();
        normalUser.setId(rs.getInt("id"));
        normalUser.setEmail(rs.getString("email"));
        normalUser.setRoles(rs.getString("roles"));
        normalUser.setPassword(rs.getString("password"));
        normalUser.setUsername(rs.getString("username"));
        normalUser.setPhone(rs.getString("phone"));
        normalUser.setAvatarUrl(rs.getString("avatar_url"));
        normalUser.setCreatedAt(getDateTime(rs, "created_at"));
        normalUser.setStatus(rs.getString("status"));
        normalUser.setDtype(rs.getString("dtype"));
        normalUser.setResetToken(rs.getString("reset_token"));
        normalUser.setResetTokenExpiresAt(getDateTime(rs, "reset_token_expires_at"));
        normalUser.setEmailVerificationToken(rs.getString("email_verification_token"));
        normalUser.setEmailVerificationTokenExpiresAt(getDateTime(rs, "email_verification_token_expires_at"));
        normalUser.setBirthDate(getDate(rs, "birth_date"));
        return normalUser;
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

    public String getLastError() {
        return lastError;
    }
}
