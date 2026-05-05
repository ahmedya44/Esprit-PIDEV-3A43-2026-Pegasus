package com.pegasus.dao;

import com.pegasus.models.Panier;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierDAO implements IDao<Panier> {

    private Connection connection = MyConnection.getInstance().getConnection();

    @Override
    public void ajouter(Panier panier) {
        String req = "INSERT INTO panier(date_creation, total) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setTimestamp(1, Timestamp.valueOf(panier.getDateCreation()));
            ps.setFloat(2, panier.getTotal());
            ps.executeUpdate();
            System.out.println("Panier ajouté !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Panier panier) {
        String req = "UPDATE panier SET date_creation=?, total=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setTimestamp(1, Timestamp.valueOf(panier.getDateCreation()));
            ps.setFloat(2, panier.getTotal());
            ps.setInt(3, panier.getId());
            ps.executeUpdate();
            System.out.println("Panier modifié !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM panier WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Panier supprimé !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Panier> getAll() {
        List<Panier> paniers = new ArrayList<>();
        String req = "SELECT * FROM panier";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Panier p = new Panier(
                        rs.getInt("id"),
                        rs.getTimestamp("date_creation").toLocalDateTime(),
                        rs.getFloat("total")
                );
                paniers.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return paniers;
    }

    @Override
    public Panier getById(int id) {
        String req = "SELECT * FROM panier WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Panier(
                        rs.getInt("id"),
                        rs.getTimestamp("date_creation").toLocalDateTime(),
                        rs.getFloat("total")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}