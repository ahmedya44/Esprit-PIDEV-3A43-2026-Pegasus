package com.pegasus.dao;

import com.pegasus.models.*;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LignePanierDAO implements IDao<LignePanier> {

    private Connection connection = MyConnection.getInstance().getConnection();

    @Override
    public void ajouter(LignePanier lignePanier) {
        String req = "INSERT INTO ligne_panier(quantite, prix_unitaire, panier_id, produit_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, lignePanier.getQuantite());
            ps.setFloat(2, lignePanier.getPrixUnitaire());
            ps.setInt(3, lignePanier.getPanier().getId());
            ps.setInt(4, lignePanier.getProduit().getId());
            ps.executeUpdate();
            System.out.println("LignePanier ajoutée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(LignePanier lignePanier) {
        String req = "UPDATE ligne_panier SET quantite=?, prix_unitaire=?, panier_id=?, produit_id=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, lignePanier.getQuantite());
            ps.setFloat(2, lignePanier.getPrixUnitaire());
            ps.setInt(3, lignePanier.getPanier().getId());
            ps.setInt(4, lignePanier.getProduit().getId());
            ps.setInt(5, lignePanier.getId());
            ps.executeUpdate();
            System.out.println("LignePanier modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM ligne_panier WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("LignePanier supprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<LignePanier> getAll() {
        List<LignePanier> lignes = new ArrayList<>();
        String req = "SELECT * FROM ligne_panier";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Panier panier = new PanierDAO().getById(rs.getInt("panier_id"));
                Produit produit = new ProduitDAO().getById(rs.getInt("produit_id"));
                LignePanier lp = new LignePanier(
                        rs.getInt("id"),
                        rs.getInt("quantite"),
                        rs.getFloat("prix_unitaire"),
                        panier,
                        produit
                );
                lignes.add(lp);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lignes;
    }

    @Override
    public LignePanier getById(int id) {
        String req = "SELECT * FROM ligne_panier WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Panier panier = new PanierDAO().getById(rs.getInt("panier_id"));
                Produit produit = new ProduitDAO().getById(rs.getInt("produit_id"));
                return new LignePanier(
                        rs.getInt("id"),
                        rs.getInt("quantite"),
                        rs.getFloat("prix_unitaire"),
                        panier,
                        produit
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}