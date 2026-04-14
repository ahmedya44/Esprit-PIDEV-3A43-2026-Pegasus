package pegasus.services;

import pegasus.entities.Participation;
import pegasus.tools.MyConnection;

import java.sql.*;

public class ServiceParticipation {
    private Connection connection = MyConnection.getInstance().getConnection();

    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    public boolean ajouter(Participation p) {
        // 1. Vérifier la capacité restante
        pegasus.entities.Evenement event = serviceEvenement.getEvenementById(p.getId_evenement());
        if (event == null || event.getCapacite_max() <= 0) {
            return false;
        }

        String req = "INSERT INTO `participation` (`user_id`, `evenement_id`, `created_at`) VALUES (?, ?, NOW())";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, p.getId_user());
            ps.setInt(2, p.getId_evenement());
            ps.executeUpdate();

            // 2. Décrémenter la capacité
            serviceEvenement.updateCapacite(p.getId_evenement(), -1);
            return true;
        } catch (SQLException ex) {
            System.err.println("Erreur SQL participation : " + ex.getMessage());
            return false;
        }
    }

    public boolean isParticipated(int userId, int eventId) {
        String req = "SELECT * FROM participation WHERE user_id = ? AND evenement_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public void annulerParticipation(int userId, int eventId) {
        String req = "DELETE FROM participation WHERE user_id = ? AND evenement_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            int rowsDeleted = ps.executeUpdate();

            // Incrémenter la capacité uniquement si une ligne a été supprimée
            if (rowsDeleted > 0) {
                serviceEvenement.updateCapacite(eventId, 1);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur annulation : " + ex.getMessage());
        }
    }

    public java.util.List<pegasus.entities.User> getParticipantsByEvenement(int eventId) {
        java.util.List<pegasus.entities.User> list = new java.util.ArrayList<>();
        String req = "SELECT u.* FROM user u JOIN participation p ON u.id = p.user_id WHERE p.evenement_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Mapping résistant aux variations de colonnes
                list.add(new pegasus.entities.User(
                    rs.getInt(1),
                    getColumnValue(rs, "nom", "username", "name", "login"),
                    getColumnValue(rs, "prenom", "last_name", "firstname", ""),
                    getColumnValue(rs, "email", "mail", "user_email", ""),
                    getColumnValue(rs, "telephone", "phone", "tel", "num_tel", "")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur SQL participants : " + ex.getMessage());
        }
        return list;
    }

    private String getColumnValue(ResultSet rs, String... variations) {
        for (String var : variations) {
            try {
                if (var.isEmpty()) continue;
                String val = rs.getString(var);
                if (val != null) return val;
            } catch (SQLException e) { }
        }
        return "";
    }
}
