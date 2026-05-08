package com.pegasus.dao;

import com.pegasus.entities.Categorie;
import com.pegasus.entities.Produit;
import com.pegasus.services.ShopSchemaService;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO implements IDao<Produit> {

    private Connection connection = MyConnection.getInstance().getConnection();

    public ProduitDAO() {
        ShopSchemaService.ensureSchema(connection);
    }

    @Override
    public void ajouter(Produit produit) {
        String req = "INSERT INTO produit(nom, description, prix, stock, image, statut, categorie_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setFloat(3, produit.getPrix());
            ps.setInt(4, produit.getStock());
            ps.setString(5, produit.getImage());
            ps.setString(6, produit.getStatut());
            if (produit.getCategorie() != null) {
                ps.setInt(7, produit.getCategorie().getId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            ps.executeUpdate();
            System.out.println("Produit ajouté !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Produit produit) {
        String req = "UPDATE produit SET nom=?, description=?, prix=?, stock=?, image=?, statut=?, categorie_id=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setFloat(3, produit.getPrix());
            ps.setInt(4, produit.getStock());
            ps.setString(5, produit.getImage());
            ps.setString(6, produit.getStatut());
            if (produit.getCategorie() != null) {
                ps.setInt(7, produit.getCategorie().getId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            ps.setInt(8, produit.getId());
            ps.executeUpdate();
            System.out.println("Produit modifié !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM produit WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produit supprimé !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        if (connection == null) {
            return produits;
        }
        String req = "SELECT * FROM produit";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Categorie categorie = null;
                int catId = rs.getInt("categorie_id");
                if (catId != 0) {
                    categorie = new CategorieDAO().getById(catId);
                }
                Produit p = new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getString("statut"),
                        categorie
                );
                produits.add(p);
            }
        } catch (SQLException e) {
            if (!isMissingTable(e)) {
                System.err.println(e.getMessage());
            }
        }
        return produits;
    }

    @Override
    public Produit getById(int id) {
        String req = "SELECT * FROM produit WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categorie categorie = null;
                int catId = rs.getInt("categorie_id");
                if (catId != 0) {
                    categorie = new CategorieDAO().getById(catId);
                }
                return new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getString("statut"),
                        categorie
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    public boolean existsByNom(String nom) {
        String req = "SELECT COUNT(*) FROM produit WHERE nom = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean existsByNomExceptId(String nom, int id) {
        String req = "SELECT COUNT(*) FROM produit WHERE nom = ? AND id != ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, nom);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    private boolean isMissingTable(SQLException e) {
        return "42S02".equals(e.getSQLState()) || e.getErrorCode() == 1146;
    }
}
