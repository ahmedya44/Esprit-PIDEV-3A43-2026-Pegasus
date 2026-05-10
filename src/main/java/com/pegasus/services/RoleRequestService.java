package com.pegasus.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pegasus.entities.RoleRequest;
import com.pegasus.entities.User;
import com.pegasus.tools.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleRequestService {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";

    private final Gson gson = new Gson();
    private String lastError;

    public RoleRequestService() {
        ensureRoleRequestsSchema();
    }

    public String getLastError() {
        return lastError;
    }

    public boolean createRequest(User user, String requestedRole, Map<String, String> details) {
        lastError = null;
        if (user == null || user.getId() == null) {
            lastError = "User session is invalid.";
            return false;
        }
        if (!"normal_user".equalsIgnoreCase(user.getDtype())) {
            lastError = "Only normal users can request a role change.";
            return false;
        }
        if (!"artiste".equalsIgnoreCase(requestedRole) && !"sponsor".equalsIgnoreCase(requestedRole)) {
            lastError = "Requested role must be artiste or sponsor.";
            return false;
        }
        if (requestedRole.equalsIgnoreCase(user.getDtype())) {
            lastError = "You already have this role.";
            return false;
        }

        try (Connection connection = dbConnection.getConnection()) {
            if (hasPendingRequest(connection, user.getId())) {
                lastError = "You already have a pending request.";
                return false;
            }
            String sql = """
                    INSERT INTO role_request
                    (user_id, requested_role, request_data_json, status, created_at)
                    VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                    """;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, user.getId());
                statement.setString(2, requestedRole.toLowerCase());
                statement.setString(3, gson.toJson(details));
                statement.setString(4, STATUS_PENDING);
                statement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            lastError = e.getMessage();
            return false;
        }
    }

    public List<RoleRequest> findAllRequests() {
        List<RoleRequest> requests = new ArrayList<>();
        String sql = """
                SELECT rr.id, rr.user_id, rr.requested_role, rr.request_data_json, rr.status,
                       rr.reviewed_by, rr.rejection_reason, rr.created_at, rr.reviewed_at,
                       u.username, u.email, u.dtype
                FROM role_request rr
                JOIN user u ON u.id = rr.user_id
                ORDER BY rr.created_at DESC
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                requests.add(mapRoleRequest(rs));
            }
        } catch (Exception e) {
            lastError = e.getMessage();
        }
        return requests;
    }

    public List<RoleRequest> findRequestsByUserId(Integer userId) {
        List<RoleRequest> requests = new ArrayList<>();
        if (userId == null) {
            return requests;
        }
        String sql = """
                SELECT rr.id, rr.user_id, rr.requested_role, rr.request_data_json, rr.status,
                       rr.reviewed_by, rr.rejection_reason, rr.created_at, rr.reviewed_at,
                       u.username, u.email, u.dtype
                FROM role_request rr
                JOIN user u ON u.id = rr.user_id
                WHERE rr.user_id = ?
                ORDER BY rr.created_at DESC
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRoleRequest(rs));
                }
            }
        } catch (Exception e) {
            lastError = e.getMessage();
        }
        return requests;
    }

    public boolean approveRequest(RoleRequest request, User adminUser) {
        lastError = null;
        if (request == null || request.getId() == null || request.getUserId() == null) {
            lastError = "Invalid request.";
            return false;
        }
        if (adminUser == null || adminUser.getId() == null) {
            lastError = "Admin session is invalid.";
            return false;
        }

        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                RoleRequest freshRequest = getRequestById(connection, request.getId());
                if (freshRequest == null) {
                    lastError = "Request not found.";
                    connection.rollback();
                    return false;
                }
                if (!STATUS_PENDING.equalsIgnoreCase(freshRequest.getStatus())) {
                    lastError = "Only pending requests can be approved.";
                    connection.rollback();
                    return false;
                }

                applyRolePromotion(connection, freshRequest);
                updateRequestStatus(connection, freshRequest.getId(), STATUS_APPROVED, adminUser.getId(), null);
                connection.commit();
                notifyUserRequestDecision(freshRequest, true);
                return true;
            } catch (Exception e) {
                connection.rollback();
                lastError = e.getMessage();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            lastError = e.getMessage();
            return false;
        }
    }

    public boolean rejectRequest(RoleRequest request, User adminUser, String reason) {
        lastError = null;
        if (request == null || request.getId() == null) {
            lastError = "Invalid request.";
            return false;
        }
        if (adminUser == null || adminUser.getId() == null) {
            lastError = "Admin session is invalid.";
            return false;
        }
        try (Connection connection = dbConnection.getConnection()) {
            RoleRequest freshRequest = getRequestById(connection, request.getId());
            if (freshRequest == null) {
                lastError = "Request not found.";
                return false;
            }
            if (!STATUS_PENDING.equalsIgnoreCase(freshRequest.getStatus())) {
                lastError = "Only pending requests can be rejected.";
                return false;
            }
            updateRequestStatus(connection, freshRequest.getId(), STATUS_REJECTED, adminUser.getId(), reason);
            freshRequest.setRejectionReason(reason);
            notifyUserRequestDecision(freshRequest, false);
            return true;
        } catch (Exception e) {
            lastError = e.getMessage();
            return false;
        }
    }

    private void applyRolePromotion(Connection connection, RoleRequest request) throws Exception {
        JsonObject details = gson.fromJson(request.getRequestDataJson(), JsonObject.class);
        String requestedRole = request.getRequestedRole().toLowerCase();
        if ("artiste".equals(requestedRole)) {
            promoteToArtiste(connection, request.getUserId(), details);
            deleteFromNormalUser(connection, request.getUserId());
            updateUserRole(connection, request.getUserId(), "artiste", "[\"ROLE_ARTISTE\"]");
            return;
        }
        promoteToSponsor(connection, request.getUserId(), details);
        deleteFromNormalUser(connection, request.getUserId());
        updateUserRole(connection, request.getUserId(), "sponsor", "[\"ROLE_SPONSOR\"]");
    }

    private void promoteToArtiste(Connection connection, Integer userId, JsonObject details) throws Exception {
        String sql = """
                INSERT INTO artiste (id, bio, styles, facebook, instagram, portfolio_url, verified, birth_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    bio = VALUES(bio),
                    styles = VALUES(styles),
                    facebook = VALUES(facebook),
                    instagram = VALUES(instagram),
                    portfolio_url = VALUES(portfolio_url),
                    verified = VALUES(verified),
                    birth_date = VALUES(birth_date)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, getJsonValue(details, "bio"));
            statement.setString(3, getJsonValue(details, "styles"));
            statement.setString(4, getJsonValue(details, "facebook"));
            statement.setString(5, getJsonValue(details, "instagram"));
            statement.setString(6, getJsonValue(details, "portfolioUrl"));
            statement.setBoolean(7, false);
            LocalDate birthDate = getNormalUserBirthDate(connection, userId);
            if (birthDate != null) {
                statement.setDate(8, java.sql.Date.valueOf(birthDate));
            } else {
                statement.setDate(8, null);
            }
            statement.executeUpdate();
        }
    }

    private void promoteToSponsor(Connection connection, Integer userId, JsonObject details) throws Exception {
        String table = "sponsor";
        if (!tableExists(connection, table)) {
            throw new IllegalStateException("Sponsor table not found in database.");
        }

        List<String> columns = getColumns(connection, table);
        if (!columns.contains("id")) {
            throw new IllegalStateException("Sponsor table must contain id column linked to user.");
        }

        String companyName = getJsonValue(details, "companyName");
        String website = getJsonValue(details, "website");
        String address = getJsonValue(details, "address");
        String description = getJsonValue(details, "description");

        String sql = "INSERT INTO " + table + " (id" +
                (columns.contains("company_name") ? ", company_name" : "") +
                (columns.contains("website") ? ", website" : "") +
                (columns.contains("address") ? ", address" : "") +
                (columns.contains("description") ? ", description" : "") +
                (columns.contains("verified") ? ", verified" : "") +
                ") VALUES (?" +
                (columns.contains("company_name") ? ", ?" : "") +
                (columns.contains("website") ? ", ?" : "") +
                (columns.contains("address") ? ", ?" : "") +
                (columns.contains("description") ? ", ?" : "") +
                (columns.contains("verified") ? ", ?" : "") +
                ") ON DUPLICATE KEY UPDATE id = VALUES(id)" +
                (columns.contains("company_name") ? ", company_name = VALUES(company_name)" : "") +
                (columns.contains("website") ? ", website = VALUES(website)" : "") +
                (columns.contains("address") ? ", address = VALUES(address)" : "") +
                (columns.contains("description") ? ", description = VALUES(description)" : "") +
                (columns.contains("verified") ? ", verified = VALUES(verified)" : "");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int idx = 1;
            statement.setInt(idx++, userId);
            if (columns.contains("company_name")) {
                statement.setString(idx++, companyName);
            }
            if (columns.contains("website")) {
                statement.setString(idx++, website);
            }
            if (columns.contains("address")) {
                statement.setString(idx++, address);
            }
            if (columns.contains("description")) {
                statement.setString(idx++, description);
            }
            if (columns.contains("verified")) {
                statement.setBoolean(idx++, false);
            }
            statement.executeUpdate();
        }
    }

    private void updateUserRole(Connection connection, Integer userId, String dtype, String rolesJson) throws Exception {
        String sql = "UPDATE user SET dtype = ?, roles = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dtype);
            statement.setString(2, rolesJson);
            statement.setInt(3, userId);
            statement.executeUpdate();
        }
    }

    private boolean hasPendingRequest(Connection connection, Integer userId) throws Exception {
        String sql = "SELECT COUNT(*) FROM role_request WHERE user_id = ? AND status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, STATUS_PENDING);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private RoleRequest getRequestById(Connection connection, Integer requestId) throws Exception {
        String sql = """
                SELECT rr.id, rr.user_id, rr.requested_role, rr.request_data_json, rr.status,
                       rr.reviewed_by, rr.rejection_reason, rr.created_at, rr.reviewed_at,
                       u.username, u.email, u.dtype
                FROM role_request rr
                JOIN user u ON u.id = rr.user_id
                WHERE rr.id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, requestId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRoleRequest(rs);
                }
                return null;
            }
        }
    }

    private void updateRequestStatus(Connection connection, Integer requestId, String status, Integer adminId, String reason) throws Exception {
        String sql = """
                UPDATE role_request
                SET status = ?, reviewed_by = ?, reviewed_at = CURRENT_TIMESTAMP, rejection_reason = ?
                WHERE id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, adminId);
            statement.setString(3, reason);
            statement.setInt(4, requestId);
            statement.executeUpdate();
        }
    }

    private RoleRequest mapRoleRequest(ResultSet rs) throws Exception {
        RoleRequest request = new RoleRequest();
        request.setId(rs.getInt("id"));
        request.setUserId(rs.getInt("user_id"));
        request.setRequestedRole(rs.getString("requested_role"));
        request.setRequestDataJson(rs.getString("request_data_json"));
        request.setStatus(rs.getString("status"));
        request.setReviewedBy((Integer) rs.getObject("reviewed_by"));
        request.setRejectionReason(rs.getString("rejection_reason"));
        request.setUsername(rs.getString("username"));
        request.setEmail(rs.getString("email"));
        request.setCurrentRole(rs.getString("dtype"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp reviewedAt = rs.getTimestamp("reviewed_at");
        request.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
        request.setReviewedAt(reviewedAt == null ? null : reviewedAt.toLocalDateTime());
        return request;
    }

    private String getJsonValue(JsonObject object, String key) {
        if (object == null || !object.has(key) || object.get(key).isJsonNull()) {
            return null;
        }
        String value = object.get(key).getAsString();
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private LocalDate parseDate(String value) {
        try {
            return value == null ? null : LocalDate.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate getNormalUserBirthDate(Connection connection, Integer userId) throws Exception {
        String sql = "SELECT birth_date FROM normal_user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && rs.getDate(1) != null) {
                    return rs.getDate(1).toLocalDate();
                }
                return null;
            }
        }
    }

    private void deleteFromNormalUser(Connection connection, Integer userId) throws Exception {
        String sql = "DELETE FROM normal_user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    private void ensureRoleRequestsSchema() {
        String sql = """
                CREATE TABLE IF NOT EXISTS role_request (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    requested_role VARCHAR(30) NOT NULL,
                    request_data_json TEXT NULL,
                    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                    reviewed_by INT NULL,
                    rejection_reason VARCHAR(255) NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    reviewed_at TIMESTAMP NULL,
                    INDEX idx_role_request_user (user_id),
                    INDEX idx_role_request_status (status),
                    CONSTRAINT fk_role_request_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
                    CONSTRAINT fk_role_request_admin FOREIGN KEY (reviewed_by) REFERENCES user(id) ON DELETE SET NULL
                )
                """;
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            // Allow app to continue; errors are surfaced by operations.
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws Exception {
        String sql = "SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private List<String> getColumns(Connection connection, String tableName) throws Exception {
        List<String> columns = new ArrayList<>();
        String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    columns.add(rs.getString(1).toLowerCase());
                }
            }
        }
        return columns;
    }

    private void notifyUserRequestDecision(RoleRequest request, boolean approved) {
        if (request == null || request.getEmail() == null || request.getEmail().isBlank()) {
            return;
        }
        try {
            EmailService emailService = new EmailService();
            if (approved) {
                emailService.sendRoleRequestApprovedEmail(
                        request.getEmail(),
                        safeValue(request.getUsername(), "there"),
                        safeValue(request.getRequestedRole(), "requested role")
                );
            } else {
                emailService.sendRoleRequestRejectedEmail(
                        request.getEmail(),
                        safeValue(request.getUsername(), "there"),
                        safeValue(request.getRequestedRole(), "the requested role"),
                        safeValue(request.getRejectionReason(), "No reason provided")
                );
            }
        } catch (Exception ignored) {
            // Keep role moderation flow successful even if mailing fails.
        }
    }

    private String safeValue(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }
}
