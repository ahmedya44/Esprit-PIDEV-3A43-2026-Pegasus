package com.pegasus.services;

import com.pegasus.entities.Evenement;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceEvenement implements IService<Evenement> {

    private Connection connection = MyConnection.getInstance().getConnection();

    @Override
    public void ajouter(Evenement e) {
        String req = "INSERT INTO `evenement`(`titre`, `date`, `heure`, `lieu`, `description`, `image`, `capacite_max`, `prix`, `statut`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, e.getTitre());
            ps.setString(2, e.getDate());
            ps.setString(3, e.getHeure());
            ps.setString(4, e.getLieu());
            ps.setString(5, e.getDescription());
            ps.setString(6, e.getImage());
            ps.setInt(7, e.getCapacite_max());
            ps.setFloat(8, e.getPrix());
            ps.setString(9, e.getStatut());

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erreur d'ajout : " + ex.getMessage());
        }
    }

    private String lastError;

    public String getLastError() {
        return lastError;
    }

    @Override
    public void supprimer(Evenement e) {
        lastError = null;
        String req = "DELETE FROM `evenement` WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, e.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            lastError = ex.getMessage();
            System.err.println("Erreur de suppression : " + ex.getMessage());
        }
    }

    @Override
    public void modifier(Evenement e) {
        String req = "UPDATE `evenement` SET `titre`=?, `date`=?, `heure`=?, `lieu`=?, `description`=?, `image`=?, `capacite_max`=?, `prix`=?, `statut`=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, e.getTitre());
            ps.setString(2, e.getDate());
            ps.setString(3, e.getHeure());
            ps.setString(4, e.getLieu());
            ps.setString(5, e.getDescription());
            ps.setString(6, e.getImage());
            ps.setInt(7, e.getCapacite_max());
            ps.setFloat(8, e.getPrix());
            ps.setString(9, e.getStatut());
            ps.setInt(10, e.getId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erreur de modification : " + ex.getMessage());
        }
    }

    @Override
    public void getAll() {
        String req = "SELECT * FROM evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement e = new Evenement(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("date"),
                        rs.getString("heure"),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("capacite_max"),
                        rs.getFloat("prix"),
                        rs.getString("statut")
                );
                System.out.println(e);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de lecture : " + ex.getMessage());
        }
    }

    public List<Evenement> afficherEvenements() {
        List<Evenement> list = new ArrayList<>();
        if (connection == null) {
            return list;
        }
        String req = "SELECT * FROM evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement e = new Evenement(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("date"),
                        rs.getString("heure"),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("capacite_max"),
                        rs.getFloat("prix"),
                        rs.getString("statut")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de lecture : " + ex.getMessage());
        }
        return list;
    }

    public void updateCapacite(int id, int delta) {
        String req = "UPDATE `evenement` SET `capacite_max` = `capacite_max` + ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, delta);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur mise à jour capacité : " + ex.getMessage());
        }
    }

    @Override
    public void getOneById(int id) {
        Evenement e = getEvenementById(id);
        if (e != null) {
            System.out.println("Trouvé : " + e);
        } else {
            System.out.println("Aucun événement avec l'ID : " + id);
        }
    }

    public Evenement getEvenementById(int id) {
        String req = "SELECT * FROM evenement WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Evenement(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("date"),
                        rs.getString("heure"),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("capacite_max"),
                        rs.getFloat("prix"),
                        rs.getString("statut")
                );
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de lecture : " + ex.getMessage());
        }
        return null;
    }
    public void updateStatut(int id, String statut) {
        String req = "UPDATE `evenement` SET `statut` = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, statut);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur mise à jour statut : " + ex.getMessage());
        }
    }
}
