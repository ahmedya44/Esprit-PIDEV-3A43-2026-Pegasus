package com.pegasus.services;

import com.pegasus.entities.User;
import com.pegasus.tools.dbConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class ServiceUser implements IService<User> {
    private static final String PROVIDER_LOCAL = "LOCAL";
    private static final String PROVIDER_GOOGLE = "GOOGLE";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PENDING_VERIFICATION = "PENDING_VERIFICATION";
    private final Connection connection;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private String lastError;

    public ServiceUser() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(dbConnection.buildConnectionErrorMessage(e), e);
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
        String provider = normalizeProvider(user.getProvider());
        String passwordToStore = hashPasswordIfNeeded(user.getPassword());
        String req = "INSERT INTO `user`(`email`,`provider`,`google_sub`,`roles`,`password`,`username`,`phone`,`avatar_url`,`created_at`,`status`,`dtype`,`email_verification_token`,`email_verification_token_expires_at`) VALUES (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP(),?,?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, provider);
            statement.setString(3, trimToNull(user.getGoogleSub()));
            statement.setString(4, user.getRoles());
            statement.setString(5, passwordToStore);
            statement.setString(6, user.getUsername());
            statement.setString(7, user.getPhone());
            statement.setString(8, user.getAvatarUrl());
            statement.setString(9, user.getStatus());
            statement.setString(10, user.getDtype());
            statement.setString(11, trimToNull(user.getEmailVerificationToken()));
            statement.setTimestamp(12, toTimestamp(user.getEmailVerificationTokenExpiresAt()));
            statement.executeUpdate();
            user.setProvider(provider);
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
        lastError = null;
        String validationError = validateUser(user);
        if (validationError != null) {
            lastError = validationError;
            System.err.println(validationError);
            return;
        }
        String provider = normalizeProvider(user.getProvider());
        String passwordToStore = hashPasswordIfNeeded(user.getPassword());
        String req = "UPDATE `user` SET `email`=?,`provider`=?,`google_sub`=?,`roles`=?,`password`=?,`username`=?,`phone`=?,`avatar_url`=?,`status`=?,`dtype`=?,`email_verification_token`=?,`email_verification_token_expires_at`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, provider);
            statement.setString(3, trimToNull(user.getGoogleSub()));
            statement.setString(4, user.getRoles());
            statement.setString(5, passwordToStore);
            statement.setString(6, user.getUsername());
            statement.setString(7, user.getPhone());
            statement.setString(8, user.getAvatarUrl());
            statement.setString(9, user.getStatus());
            statement.setString(10, user.getDtype());
            statement.setString(11, trimToNull(user.getEmailVerificationToken()));
            statement.setTimestamp(12, toTimestamp(user.getEmailVerificationTokenExpiresAt()));
            statement.setInt(13, user.getId());
            statement.executeUpdate();
            user.setProvider(provider);
            user.setPassword(passwordToStore);
            System.out.println("user updated !");
        } catch (SQLException e) {
            lastError = e.getMessage();
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
        if ("SUSPENDED".equalsIgnoreCase(user.getStatus()) || "BANNED".equalsIgnoreCase(user.getStatus())) {
            lastError = "Your account is suspended. Please contact Pegasus staff for help.";
            return null;
        }
        if (!STATUS_ACTIVE.equalsIgnoreCase(user.getStatus())) {
            lastError = "Account is not active. Please verify your email first.";
            return null;
        }
        return verifyPassword(rawPassword, user.getPassword()) ? user : null;
    }

    public String createEmailVerificationToken(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must exist before creating a verification token.");
        }
        String token = generateVerificationToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
        String req = "UPDATE `user` SET `email_verification_token`=?, `email_verification_token_expires_at`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, token);
            statement.setTimestamp(2, toTimestamp(expiresAt));
            statement.setInt(3, user.getId());
            statement.executeUpdate();
            user.setEmailVerificationToken(token);
            user.setEmailVerificationTokenExpiresAt(expiresAt);
            return token;
        } catch (SQLException e) {
            lastError = e.getMessage();
            throw new IllegalStateException("Could not create email verification token.", e);
        }
    }

    public User verifyEmailToken(String token) {
        if (isBlank(token)) {
            lastError = "Verification code is required.";
            return null;
        }

        String req = "SELECT * FROM `user` WHERE `email_verification_token` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, token.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    lastError = "Invalid verification code.";
                    return null;
                }

                User user = mapUser(rs);
                if (user.getEmailVerificationTokenExpiresAt() == null
                        || user.getEmailVerificationTokenExpiresAt().isBefore(LocalDateTime.now())) {
                    lastError = "Verification code has expired.";
                    return null;
                }

                activateVerifiedUser(user);
                return user;
            }
        } catch (SQLException e) {
            lastError = e.getMessage();
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String createPasswordResetToken(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must exist before creating a reset token.");
        }
        String token = generateVerificationToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
        String req = "UPDATE `user` SET `reset_token`=?, `reset_token_expires_at`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, token);
            statement.setTimestamp(2, toTimestamp(expiresAt));
            statement.setInt(3, user.getId());
            statement.executeUpdate();
            user.setResetToken(token);
            user.setResetTokenExpiresAt(expiresAt);
            return token;
        } catch (SQLException e) {
            lastError = e.getMessage();
            throw new IllegalStateException("Could not create password reset token.", e);
        }
    }

    public User findByResetToken(String token) {
        if (isBlank(token)) {
            lastError = "Reset code is required.";
            return null;
        }
        String req = "SELECT * FROM `user` WHERE `reset_token` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, token.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    lastError = "Invalid reset code.";
                    return null;
                }
                User user = mapUser(rs);
                if (user.getResetTokenExpiresAt() == null || user.getResetTokenExpiresAt().isBefore(LocalDateTime.now())) {
                    lastError = "Reset code has expired.";
                    return null;
                }
                return user;
            }
        } catch (SQLException e) {
            lastError = e.getMessage();
            return null;
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        lastError = null;
        if (isBlank(newPassword)) {
            lastError = "New password is required.";
            return false;
        }
        User user = findByResetToken(token);
        if (user == null) {
            return false;
        }
        String hashed = hashPasswordIfNeeded(newPassword);
        String req = "UPDATE `user` SET `password`=?, `reset_token`=NULL, `reset_token_expires_at`=NULL WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, hashed);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            lastError = e.getMessage();
            return false;
        }
    }

    public User findByGoogleSub(String googleSub) {
        if (isBlank(googleSub)) {
            return null;
        }
        String req = "SELECT * FROM `user` WHERE `google_sub` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, googleSub.trim());
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
        user.setProvider(rs.getString("provider"));
        user.setGoogleSub(rs.getString("google_sub"));
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

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
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
        String provider = normalizeProvider(user.getProvider());
        user.setProvider(provider);
        if (PROVIDER_LOCAL.equals(provider) && isBlank(user.getPassword())) {
            return "password is required";
        }
        if (isBlank(user.getStatus())) {
            return "status is required";
        }
        if (isBlank(user.getDtype())) {
            return "dtype is required";
        }
        if (PROVIDER_GOOGLE.equals(provider) && isBlank(user.getGoogleSub())) {
            return "google_sub is required for Google accounts";
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String trimToNull(String value) {
        return isBlank(value) ? null : value.trim();
    }

    private String normalizeProvider(String provider) {
        if (isBlank(provider)) {
            return PROVIDER_LOCAL;
        }
        return provider.trim().toUpperCase();
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

    private String generateVerificationToken() {
        byte[] buffer = new byte[24];
        new SecureRandom().nextBytes(buffer);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
    }

    private void activateVerifiedUser(User user) throws SQLException {
        String req = "UPDATE `user` SET `status`=?, `email_verification_token`=NULL, `email_verification_token_expires_at`=NULL WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, STATUS_ACTIVE);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            user.setStatus(STATUS_ACTIVE);
            user.setEmailVerificationToken(null);
            user.setEmailVerificationTokenExpiresAt(null);
        }
    }

    public String getLastError() {
        return lastError;
    }
}
