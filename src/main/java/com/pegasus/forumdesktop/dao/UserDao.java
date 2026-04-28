package com.pegasus.forumdesktop.dao;

import com.pegasus.forumdesktop.config.DatabaseConfig;
import com.pegasus.forumdesktop.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM `user` WHERE LOWER(email) = LOWER(?)";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, email == null ? "" : email.trim());
            try (var rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load user by email.", ex);
        }
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM `user` WHERE id = ?";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (var rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DaoException("Could not load user.", ex);
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM `user` ORDER BY username ASC, email ASC";
        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(sql);
             var rs = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(map(rs));
            }
            return users;
        } catch (SQLException ex) {
            throw new DaoException("Could not load users.", ex);
        }
    }

    private User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setRolesJson(rs.getString("roles"));
        user.setPassword(rs.getString("password"));
        user.setUsername(rs.getString("username"));
        user.setPhone(rs.getString("phone"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setCreatedAt(JdbcMapper.dateTime(rs, "created_at"));
        user.setStatus(rs.getString("status"));
        user.setDtype(rs.getString("dtype"));
        return user;
    }
}
