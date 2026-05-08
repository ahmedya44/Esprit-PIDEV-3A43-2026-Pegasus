package com.pegasus.services;

import com.pegasus.entities.SponsoringPack;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSponsoringPack implements IService<SponsoringPack> {

    private Connection connection = MyConnection.getInstance().getConnection();
    private String lastError;

    public String getLastError() {
        return lastError;
    }

    private boolean columnExists(String table, String column) {
        String req = "SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, table);
            ps.setString(2, column);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean tableExists(String table) {
        String req = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, table);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    private String packTable() {
        boolean hasSponsoringPack = tableExists("sponsoring_pack");
        boolean hasPack = tableExists("pack");

        if (hasSponsoringPack && hasPack) {
            int sponsoringPackCount = countRows("sponsoring_pack");
            int packCount = countRows("pack");
            return packCount > sponsoringPackCount ? "pack" : "sponsoring_pack";
        }
        if (hasPack) {
            return "pack";
        }
        if (hasSponsoringPack) {
            return "sponsoring_pack";
        }
        return "pack";
    }

    private int countRows(String table) {
        String req = "SELECT COUNT(*) FROM " + table;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return 0;
        }
        return 0;
    }

    private int normalizeEvenementIdForPack(int evenementIdFromUi) {
        if (evenementIdFromUi <= 0) {
            return evenementIdFromUi;
        }

        if (!columnExists("evenement", "id_evenement")) {
            return evenementIdFromUi;
        }

        // If UI id already exists as id_evenement, keep it as-is.
        String byIdEvenement = "SELECT id_evenement FROM evenement WHERE id_evenement = ?";
        try (PreparedStatement ps = connection.prepareStatement(byIdEvenement)) {
            ps.setInt(1, evenementIdFromUi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur verification id_evenement direct: " + ex.getMessage());
        }

        // Otherwise map evenement.id -> evenement.id_evenement.
        String req = "SELECT id_evenement FROM evenement WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, evenementIdFromUi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur mapping id evenement: " + ex.getMessage());
        }
        return evenementIdFromUi;
    }

    @Override
    public void ajouter(SponsoringPack p) {
        lastError = null;
        String req = "INSERT INTO `" + packTable() + "`(`nom_pack`, `description`, `prix`, `id_evenement`, `id_sponsor`) VALUES (?,?,?,?,?)";
        try {
            int eventId = normalizeEvenementIdForPack(p.getId_evenement());
            if (eventId <= 0) {
                throw new SQLException("id_evenement invalide pour insertion pack");
            }

            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, p.getNom_pack());
            ps.setString(2, p.getDescription());
            ps.setFloat(3, p.getPrix());
            ps.setInt(4, eventId);
            if (p.getId_sponsor() <= 0) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, p.getId_sponsor());
            }

            ps.executeUpdate();

        } catch (SQLException ex) {
            lastError = ex.getMessage();
            System.err.println("Erreur d'ajout pack : " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(SponsoringPack p) {
        lastError = null;
        String req = "DELETE FROM `" + packTable() + "` WHERE id_pack = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, p.getId_pack());
            ps.executeUpdate();

        } catch (SQLException ex) {
            lastError = ex.getMessage();
            System.err.println("❌ Erreur SQL de suppression pack : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(SponsoringPack p) {
        lastError = null;
        String req = "UPDATE `" + packTable() + "` SET `nom_pack`=?, `description`=?, `prix`=?, `id_evenement`=?, `id_sponsor`=? WHERE id_pack=?";
        try {
            int eventId = normalizeEvenementIdForPack(p.getId_evenement());
            if (eventId <= 0) {
                throw new SQLException("id_evenement invalide pour mise a jour pack");
            }

            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, p.getNom_pack());
            ps.setString(2, p.getDescription());
            ps.setFloat(3, p.getPrix());
            ps.setInt(4, eventId);
            if (p.getId_sponsor() <= 0) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, p.getId_sponsor());
            }
            ps.setInt(6, p.getId_pack());
            ps.executeUpdate();

        } catch (SQLException ex) {
            lastError = ex.getMessage();
            System.err.println("Erreur de modification pack : " + ex.getMessage());
        }
    }

    public List<SponsoringPack> afficherTout() {
        List<SponsoringPack> list = new ArrayList<>();
        String req = "SELECT * FROM " + packTable();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new SponsoringPack(
                        rs.getInt("id_pack"),
                        rs.getString("nom_pack"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getInt("id_evenement"),
                        rs.getInt("id_sponsor")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de lecture packs : " + ex.getMessage());
        }
        return list;
    }

    public List<SponsoringPack> afficherParEvenement(int evenementId) {
        List<SponsoringPack> list = new ArrayList<>();
        int mappedId = normalizeEvenementIdForPack(evenementId);
        String req = "SELECT * FROM " + packTable() + " WHERE id_evenement = ? OR id_evenement = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, evenementId);
            ps.setInt(2, mappedId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SponsoringPack(
                        rs.getInt("id_pack"),
                        rs.getString("nom_pack"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getInt("id_evenement"),
                        rs.getInt("id_sponsor")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de lecture packs par evenement : " + ex.getMessage());
        }
        return list;
    }

    @Override
    public void getAll() {
        afficherTout().forEach(System.out::println);
    }

    @Override
    public void getOneById(int id) {
        String req = "SELECT * FROM " + packTable() + " WHERE id_pack = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(new SponsoringPack(
                        rs.getInt("id_pack"),
                        rs.getString("nom_pack"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getInt("id_evenement"),
                        rs.getInt("id_sponsor")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de recherche pack : " + ex.getMessage());
        }
    }

    public String getSponsorNameById(int userId) {
        if (userId == 0) return null;
        String req = "SELECT username FROM user WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur recherche sponsor : " + ex.getMessage());
        }
        return "Sponsor #" + userId;
    }

    /**
     * Returns a list of String arrays: [nom, prenom, email, telephone, nom_pack, prix]
     * for all sponsors who reserved a pack in a given event.
     */
    public java.util.List<String[]> getSponsorsForEvent(int evenementId) {
        java.util.List<String[]> sponsors = new java.util.ArrayList<>();
        int mappedId = normalizeEvenementIdForPack(evenementId);
        String req = "SELECT u.username, u.email, u.phone, sp.nom_pack, sp.prix, sp.id_pack " +
                     "FROM " + packTable() + " sp " +
                     "JOIN user u ON sp.id_sponsor = u.id " +
                     "WHERE (sp.id_evenement = ? OR sp.id_evenement = ?) AND sp.id_sponsor IS NOT NULL";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, evenementId);
            ps.setInt(2, mappedId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sponsors.add(new String[]{
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("nom_pack"),
                    String.valueOf(rs.getFloat("prix")),
                    String.valueOf(rs.getInt("id_pack"))
                });
            }
        } catch (SQLException ex) {
            System.err.println("Erreur liste sponsors : " + ex.getMessage());
        }
        return sponsors;
    }

    public void reserverPack(int packId, int sponsorId) {
        String req = "UPDATE " + packTable() + " SET id_sponsor = ? WHERE id_pack = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            if (sponsorId <= 0) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, sponsorId);
            }
            ps.setInt(2, packId);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erreur reservation pack : " + ex.getMessage());
        }
    }

    /**
     * Returns a global list of all sponsors across all events.
     * Each entry: [username, email, phone, nom_pack, prix, evenement_titre, id_pack]
     */
    public List<String[]> getSponsorsGlobalList() {
        List<String[]> list = new ArrayList<>();
        String req = "SELECT u.username, u.email, u.phone, sp.nom_pack, sp.prix, e.titre as evenement_titre, sp.id_pack " +
                     "FROM " + packTable() + " sp " +
                     "JOIN user u ON sp.id_sponsor = u.id " +
                     "JOIN evenement e ON sp.id_evenement = e.id " +
                     "WHERE sp.id_sponsor IS NOT NULL";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("nom_pack"),
                    String.valueOf(rs.getFloat("prix")),
                    rs.getString("evenement_titre"),
                    String.valueOf(rs.getInt("id_pack"))
                });
            }
        } catch (SQLException ex) {
            System.err.println("Erreur global sponsors list : " + ex.getMessage());
        }
        return list;
    }
}
