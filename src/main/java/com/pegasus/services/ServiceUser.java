package com.pegasus.services;

import com.pegasus.entities.User;
import com.pegasus.tools.dbConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ServiceUser implements IService<User> {
    private final Connection connection;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private String lastError;

    public ServiceUser() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }

    @Override
    public void ajouter(User user) {
        lastError = null;
        String validationError = validateUser(user);
        if (validationError != null) {
            lastError = validationError;
            System.err.println(validationError);
            return;
        }
        String passwordToStore = hashPasswordIfNeeded(user.getPassword());
        String req = "INSERT INTO `user`(`email`,`roles`,`password`,`username`,`phone`,`avatar_url`,`created_at`,`status`,`dtype`) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP(),?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRoles());
            statement.setString(3, passwordToStore);
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getAvatarUrl());
            statement.setString(7, user.getStatus());
            statement.setString(8, user.getDtype());
            statement.executeUpdate();
            user.setPassword(passwordToStore);

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            System.out.println("user added !");
        } catch (SQLException e) {
            lastError = e.getMessage();
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(User user) {
        if (user.getId() == null) {
            System.err.println("user id is required for delete");
            return;
        }
        String req = "DELETE FROM `user` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            System.out.println("user deleted !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) {
        if (user.getId() == null) {
            System.err.println("user id is required for update");
            return;
        }
        String validationError = validateUser(user);
        if (validationError != null) {
            System.err.println(validationError);
            return;
        }
        String passwordToStore = hashPasswordIfNeeded(user.getPassword());
        String req = "UPDATE `user` SET `email`=?,`roles`=?,`password`=?,`username`=?,`phone`=?,`avatar_url`=?,`status`=?,`dtype`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRoles());
            statement.setString(3, passwordToStore);
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getAvatarUrl());
            statement.setString(7, user.getStatus());
            statement.setString(8, user.getDtype());
            statement.setInt(9, user.getId());
            statement.executeUpdate();
            user.setPassword(passwordToStore);
            System.out.println("user updated !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public User findByEmail(String email) {
        if (isBlank(email)) {
            return null;
        }
        String req = "SELECT * FROM `user` WHERE `email` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, email.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public User authenticate(String email, String rawPassword) {
        User user = findByEmail(email);
        if (user == null || isBlank(rawPassword)) {
            return null;
        }
        return verifyPassword(rawPassword, user.getPassword()) ? user : null;
    }

    public User findById(int id) {
        String req = "SELECT * FROM `user` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM `user` ORDER BY `id` DESC";
        try (Statement st = this.connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void getAll() {
        String req = "SELECT * FROM `user`";
        try (Statement st = this.connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                User user = mapUser(rs);
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getOneById(int id) {
        String req = "SELECT * FROM `user` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = mapUser(rs);
                    System.out.println(user);
                } else {
                    System.out.println("user not found");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setRoles(rs.getString("roles"));
        user.setPassword(rs.getString("password"));
        user.setUsername(rs.getString("username"));
        user.setPhone(rs.getString("phone"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setCreatedAt(getDateTime(rs, "created_at"));
        user.setStatus(rs.getString("status"));
        user.setDtype(rs.getString("dtype"));
        user.setResetToken(rs.getString("reset_token"));
        user.setResetTokenExpiresAt(getDateTime(rs, "reset_token_expires_at"));
        user.setEmailVerificationToken(rs.getString("email_verification_token"));
        user.setEmailVerificationTokenExpiresAt(getDateTime(rs, "email_verification_token_expires_at"));
        return user;
    }

    private LocalDateTime getDateTime(ResultSet rs, String column) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(column);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    private String validateUser(User user) {
        if (user == null) {
            return "user is required";
        }
        if (isBlank(user.getEmail())) {
            return "email is required";
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail().trim()).matches()) {
            return "email format is invalid";
        }
        if (isBlank(user.getUsername())) {
            return "username is required";
        }
        if (isBlank(user.getPassword())) {
            return "password is required";
        }
        if (isBlank(user.getStatus())) {
            return "status is required";
        }
        if (isBlank(user.getDtype())) {
            return "dtype is required";
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String hashPasswordIfNeeded(String rawPassword) {
        if (isBlank(rawPassword)) {
            return rawPassword;
        }
        String value = rawPassword.trim();
        if (isBcryptHash(value)) {
            return value;
        }
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    private boolean verifyPassword(String rawPassword, String storedPassword) {
        if (isBlank(rawPassword) || isBlank(storedPassword)) {
            return false;
        }
        String raw = rawPassword.trim();
        String stored = storedPassword.trim();
        if (isBcryptHash(stored)) {
            try {
                return BCrypt.checkpw(raw, stored);
            } catch (Exception e) {
                return false;
            }
        }
        // Legacy plain-text fallback (for existing old rows created before hashing).
        return raw.equals(stored);
    }

    private boolean isBcryptHash(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }

    public String getLastError() {
        return lastError;
    }
}
