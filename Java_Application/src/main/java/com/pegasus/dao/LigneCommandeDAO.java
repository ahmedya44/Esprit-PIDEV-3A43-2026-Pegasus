package com.pegasus.dao;

import com.pegasus.entities.*;
import com.pegasus.services.ShopSchemaService;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeDAO implements IDao<LigneCommande> {

    private Connection connection = MyConnection.getInstance().getConnection();

    public LigneCommandeDAO() {
        ShopSchemaService.ensureSchema(connection);
    }

    @Override
    public void ajouter(LigneCommande ligneCommande) {
        String req = "INSERT INTO ligne_commande(quantite, prix_unitaire, commande_id, produit_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, ligneCommande.getQuantite());
            ps.setFloat(2, ligneCommande.getPrixUnitaire());
            ps.setInt(3, ligneCommande.getCommande().getId());
            ps.setInt(4, ligneCommande.getProduit().getId());
            ps.executeUpdate();
            System.out.println("LigneCommande ajoutée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(LigneCommande ligneCommande) {
        String req = "UPDATE ligne_commande SET quantite=?, prix_unitaire=?, commande_id=?, produit_id=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, ligneCommande.getQuantite());
            ps.setFloat(2, ligneCommande.getPrixUnitaire());
            ps.setInt(3, ligneCommande.getCommande().getId());
            ps.setInt(4, ligneCommande.getProduit().getId());
            ps.setInt(5, ligneCommande.getId());
            ps.executeUpdate();
            System.out.println("LigneCommande modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM ligne_commande WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("LigneCommande supprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<LigneCommande> getAll() {
        List<LigneCommande> lignes = new ArrayList<>();
        String req = "SELECT lc.*, p.nom as prod_nom, p.prix as prod_prix FROM ligne_commande lc LEFT JOIN produit p ON lc.produit_id = p.id";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Commande commande = new CommandeDAO().getById(rs.getInt("commande_id"));
                Produit produit = new ProduitDAO().getById(rs.getInt("produit_id"));
                LigneCommande lc = new LigneCommande(
                        rs.getInt("id"),
                        rs.getInt("quantite"),
                        rs.getFloat("prix_unitaire"),
                        commande,
                        produit
                );
                lignes.add(lc);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lignes;
    }

    @Override
    public LigneCommande getById(int id) {
        String req = "SELECT * FROM ligne_commande WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Commande commande = new CommandeDAO().getById(rs.getInt("commande_id"));
                Produit produit = new ProduitDAO().getById(rs.getInt("produit_id"));
                return new LigneCommande(
                        rs.getInt("id"),
                        rs.getInt("quantite"),
                        rs.getFloat("prix_unitaire"),
                        commande,
                        produit
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<LigneCommande> getByCommandeId(int commandeId) {
        List<LigneCommande> lignes = new ArrayList<>();
        String req = "SELECT * FROM ligne_commande WHERE commande_id=? ORDER BY id";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Commande commande = new CommandeDAO().getById(rs.getInt("commande_id"));
                Produit produit = new ProduitDAO().getById(rs.getInt("produit_id"));
                lignes.add(new LigneCommande(
                        rs.getInt("id"),
                        rs.getInt("quantite"),
                        rs.getFloat("prix_unitaire"),
                        commande,
                        produit
                ));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lignes;
    }
}
