package com.pegasus.services;

import com.pegasus.entities.Produit;
import com.pegasus.tools.MyConnection;

import java.sql.*;
import java.util.*;

public class StatsService {

    private static final Connection connection =
            MyConnection.getInstance().getConnection();

    static {
        ShopSchemaService.ensureSchema(connection);
    }

    // ── Likes par produit ──────────────────────────────────────────────
    public static Map<Produit, Integer> getLikesParProduit(List<Produit> produits) {
        Map<Produit, Integer> result = new LinkedHashMap<>();
        for (Produit p : produits) {
            result.put(p, LikeService.isLiked(p.getId()) ? 1 : 0);
        }
        return result;
    }

    // ── Achats par produit (depuis commandes payées) ───────────────────
    public static Map<String, Integer> getAchatsParProduit() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String req = """
            SELECT p.nom, SUM(lp.quantite) AS total_achats
            FROM ligne_commande lp
            JOIN produit p ON lp.produit_id = p.id
            JOIN commande c ON lp.commande_id = c.id
            WHERE c.statut = 'payee'
            GROUP BY p.nom
            ORDER BY total_achats DESC
        """;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                result.put(rs.getString("nom"), rs.getInt("total_achats"));
            }
        } catch (SQLException e) {
            System.err.println("getAchatsParProduit: " + e.getMessage());
        }
        return result;
    }

    // ── Montant total des ventes par produit ───────────────────────────
    public static Map<String, Double> getMontantParProduit() {
        Map<String, Double> result = new LinkedHashMap<>();
        String req = """
            SELECT p.nom, SUM(lp.quantite * lp.prix_unitaire) AS montant_total
            FROM ligne_commande lp
            JOIN produit p ON lp.produit_id = p.id
            JOIN commande c ON lp.commande_id = c.id
            WHERE c.statut = 'payee'
            GROUP BY p.nom
            ORDER BY montant_total DESC
        """;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                result.put(rs.getString("nom"), rs.getDouble("montant_total"));
            }
        } catch (SQLException e) {
            System.err.println("getMontantParProduit: " + e.getMessage());
        }
        return result;
    }

    // ── Total global des ventes ────────────────────────────────────────
    public static double getTotalVentes() {
        String req = "SELECT COALESCE(SUM(total), 0) FROM commande WHERE statut = 'payee'";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("getTotalVentes: " + e.getMessage());
        }
        return 0;
    }

    // ── Nombre total de commandes ──────────────────────────────────────
    public static int getTotalCommandes() {
        String req = "SELECT COUNT(*) FROM commande WHERE statut = 'payee'";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("getTotalCommandes: " + e.getMessage());
        }
        return 0;
    }
}
