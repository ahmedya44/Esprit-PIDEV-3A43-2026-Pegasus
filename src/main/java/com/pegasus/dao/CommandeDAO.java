package com.pegasus.dao;

import com.pegasus.models.Commande;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO implements IDao<Commande> {

    private Connection connection = MyConnection.getInstance().getConnection();

    @Override
    public void ajouter(Commande commande) {
        String req = "INSERT INTO commande(date_commande, statut, total) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setTimestamp(1, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(2, commande.getStatut());
            ps.setFloat(3, commande.getTotal());
            ps.executeUpdate();
            System.out.println("Commande ajoutée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Commande commande) {
        String req = "UPDATE commande SET date_commande=?, statut=?, total=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setTimestamp(1, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(2, commande.getStatut());
            ps.setFloat(3, commande.getTotal());
            ps.setInt(4, commande.getId());
            ps.executeUpdate();
            System.out.println("Commande modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM commande WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commande supprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("id"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut"),
                        rs.getFloat("total")
                );
                commandes.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commandes;
    }

    @Override
    public Commande getById(int id) {
        String req = "SELECT * FROM commande WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Commande(
                        rs.getInt("id"),
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getString("statut"),
                        rs.getFloat("total")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
