package com.pegasus.services;

import com.pegasus.entities.Artiste;
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

public class ServiceArtiste implements IService<Artiste> {
    private final Connection connection;

    public ServiceArtiste() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to database", e);
        }
    }

    @Override
    public void ajouter(Artiste artiste) {
        if (artiste.getId() == null) {
            System.err.println("artiste id is required (must exist in user table)");
            return;
        }
        String req = "INSERT INTO `artiste`(`bio`,`styles`,`facebook`,`instagram`,`portfolio_url`,`verified`,`id`,`birth_date`) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, artiste.getBio());
            statement.setString(2, artiste.getStyles());
            statement.setString(3, artiste.getFacebook());
            statement.setString(4, artiste.getInstagram());
            statement.setString(5, artiste.getPortfolioUrl());
            statement.setBoolean(6, artiste.isVerified());
            statement.setInt(7, artiste.getId());
            setDate(statement, 8, artiste.getBirthDate());
            statement.executeUpdate();
            System.out.println("artiste added !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Artiste artiste) {
        if (artiste.getId() == null) {
            System.err.println("artiste id is required for delete");
            return;
        }
        String req = "DELETE FROM `artiste` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, artiste.getId());
            statement.executeUpdate();
            System.out.println("artiste deleted !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Artiste artiste) {
        if (artiste.getId() == null) {
            System.err.println("artiste id is required for update");
            return;
        }
        String req = "UPDATE `artiste` SET `bio`=?,`styles`=?,`facebook`=?,`instagram`=?,`portfolio_url`=?,`verified`=?,`birth_date`=? WHERE `id`=?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setString(1, artiste.getBio());
            statement.setString(2, artiste.getStyles());
            statement.setString(3, artiste.getFacebook());
            statement.setString(4, artiste.getInstagram());
            statement.setString(5, artiste.getPortfolioUrl());
            statement.setBoolean(6, artiste.isVerified());
            setDate(statement, 7, artiste.getBirthDate());
            statement.setInt(8, artiste.getId());
            statement.executeUpdate();
            System.out.println("artiste updated !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, a.bio, a.styles, a.facebook, a.instagram, a.portfolio_url, a.verified, a.birth_date FROM `artiste` a JOIN `user` u ON a.id = u.id";
        try (Statement st = this.connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Artiste artiste = mapArtiste(rs);
                System.out.println(artiste);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getOneById(int id) {
        String req = "SELECT u.id, u.email, u.roles, u.password, u.username, u.phone, u.avatar_url, u.created_at, u.status, u.dtype, u.reset_token, u.reset_token_expires_at, u.email_verification_token, u.email_verification_token_expires_at, a.bio, a.styles, a.facebook, a.instagram, a.portfolio_url, a.verified, a.birth_date FROM `artiste` a JOIN `user` u ON a.id = u.id WHERE a.id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(req)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Artiste artiste = mapArtiste(rs);
                    System.out.println(artiste);
                } else {
                    System.out.println("artiste not found");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Artiste mapArtiste(ResultSet rs) throws SQLException {
        Artiste artiste = new Artiste();
        artiste.setId(rs.getInt("id"));
        artiste.setEmail(rs.getString("email"));
        artiste.setRoles(rs.getString("roles"));
        artiste.setPassword(rs.getString("password"));
        artiste.setUsername(rs.getString("username"));
        artiste.setPhone(rs.getString("phone"));
        artiste.setAvatarUrl(rs.getString("avatar_url"));
        artiste.setCreatedAt(getDateTime(rs, "created_at"));
        artiste.setStatus(rs.getString("status"));
        artiste.setDtype(rs.getString("dtype"));
        artiste.setResetToken(rs.getString("reset_token"));
        artiste.setResetTokenExpiresAt(getDateTime(rs, "reset_token_expires_at"));
        artiste.setEmailVerificationToken(rs.getString("email_verification_token"));
        artiste.setEmailVerificationTokenExpiresAt(getDateTime(rs, "email_verification_token_expires_at"));
        artiste.setBio(rs.getString("bio"));
        artiste.setStyles(rs.getString("styles"));
        artiste.setFacebook(rs.getString("facebook"));
        artiste.setInstagram(rs.getString("instagram"));
        artiste.setPortfolioUrl(rs.getString("portfolio_url"));
        artiste.setVerified(rs.getBoolean("verified"));
        artiste.setBirthDate(getDate(rs, "birth_date"));
        return artiste;
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
