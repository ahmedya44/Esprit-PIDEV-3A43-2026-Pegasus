package pegasus.services;

import pegasus.entities.SponsoringPack;
import pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSponsoringPack implements IService<SponsoringPack> {

    private Connection connection = MyConnection.getInstance().getConnection();

    @Override
    public void ajouter(SponsoringPack p) {
        String req = "INSERT INTO `sponsoring_pack`(`nom_pack`, `description`, `prix`, `id_evenement`, `id_sponsor`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, p.getNom_pack());
            ps.setString(2, p.getDescription());
            ps.setFloat(3, p.getPrix());
            ps.setInt(4, p.getId_evenement());
            ps.setInt(5, p.getId_sponsor());

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erreur d'ajout pack : " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(SponsoringPack p) {
        String req = "DELETE FROM `sponsoring_pack` WHERE id_pack = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, p.getId_pack());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("❌ Erreur SQL de suppression pack : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(SponsoringPack p) {
        String req = "UPDATE `sponsoring_pack` SET `nom_pack`=?, `description`=?, `prix`=?, `id_evenement`=?, `id_sponsor`=? WHERE id_pack=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, p.getNom_pack());
            ps.setString(2, p.getDescription());
            ps.setFloat(3, p.getPrix());
            ps.setInt(4, p.getId_evenement());
            ps.setInt(5, p.getId_sponsor());
            ps.setInt(6, p.getId_pack());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erreur de modification pack : " + ex.getMessage());
        }
    }

    public List<SponsoringPack> afficherTout() {
        List<SponsoringPack> list = new ArrayList<>();
        String req = "SELECT * FROM sponsoring_pack";
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
        String req = "SELECT * FROM sponsoring_pack WHERE id_evenement = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, evenementId);
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
        String req = "SELECT * FROM sponsoring_pack WHERE id_pack = ?";
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
        String req = "SELECT u.username, u.email, u.phone, sp.nom_pack, sp.prix, sp.id_pack " +
                     "FROM sponsoring_pack sp " +
                     "JOIN user u ON sp.id_sponsor = u.id " +
                     "WHERE sp.id_evenement = ? AND sp.id_sponsor != 0";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, evenementId);
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
        String req = "UPDATE sponsoring_pack SET id_sponsor = ? WHERE id_pack = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, sponsorId);
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
                     "FROM sponsoring_pack sp " +
                     "JOIN user u ON sp.id_sponsor = u.id " +
                     "JOIN evenement e ON sp.id_evenement = e.id " +
                     "WHERE sp.id_sponsor != 0";
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
