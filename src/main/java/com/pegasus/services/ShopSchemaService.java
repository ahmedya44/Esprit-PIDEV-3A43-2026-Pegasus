package com.pegasus.services;

import com.pegasus.tools.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class ShopSchemaService {
    private static boolean initialized;

    private ShopSchemaService() {
    }

    public static synchronized void ensureSchema() {
        ensureSchema(MyConnection.getInstance().getConnection());
    }

    public static synchronized void ensureSchema(Connection connection) {
        if (initialized || connection == null) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS categorie (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(160) NOT NULL,
                        description TEXT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS produit (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(180) NOT NULL,
                        description TEXT NULL,
                        prix DECIMAL(10, 2) NOT NULL DEFAULT 0,
                        stock INT NOT NULL DEFAULT 0,
                        image VARCHAR(700) NULL,
                        statut VARCHAR(50) NOT NULL DEFAULT 'disponible',
                        categorie_id INT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        INDEX idx_produit_categorie (categorie_id),
                        INDEX idx_produit_statut (statut),
                        CONSTRAINT fk_produit_categorie
                            FOREIGN KEY (categorie_id) REFERENCES categorie(id)
                            ON UPDATE CASCADE ON DELETE SET NULL
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS panier (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        total DECIMAL(10, 2) NOT NULL DEFAULT 0
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS commande (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        date_commande DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        statut VARCHAR(50) NOT NULL DEFAULT 'en_attente',
                        total DECIMAL(10, 2) NOT NULL DEFAULT 0,
                        INDEX idx_commande_statut (statut),
                        INDEX idx_commande_date (date_commande)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS ligne_panier (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        quantite INT NOT NULL DEFAULT 1,
                        prix_unitaire DECIMAL(10, 2) NOT NULL DEFAULT 0,
                        panier_id INT NOT NULL,
                        produit_id INT NOT NULL,
                        INDEX idx_ligne_panier_panier (panier_id),
                        INDEX idx_ligne_panier_produit (produit_id),
                        CONSTRAINT fk_ligne_panier_panier
                            FOREIGN KEY (panier_id) REFERENCES panier(id)
                            ON UPDATE CASCADE ON DELETE CASCADE,
                        CONSTRAINT fk_ligne_panier_produit
                            FOREIGN KEY (produit_id) REFERENCES produit(id)
                            ON UPDATE CASCADE ON DELETE RESTRICT
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS ligne_commande (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        quantite INT NOT NULL DEFAULT 1,
                        prix_unitaire DECIMAL(10, 2) NOT NULL DEFAULT 0,
                        commande_id INT NOT NULL,
                        produit_id INT NOT NULL,
                        INDEX idx_ligne_commande_commande (commande_id),
                        INDEX idx_ligne_commande_produit (produit_id),
                        CONSTRAINT fk_ligne_commande_commande
                            FOREIGN KEY (commande_id) REFERENCES commande(id)
                            ON UPDATE CASCADE ON DELETE CASCADE,
                        CONSTRAINT fk_ligne_commande_produit
                            FOREIGN KEY (produit_id) REFERENCES produit(id)
                            ON UPDATE CASCADE ON DELETE RESTRICT
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                    """);

            initialized = true;
        } catch (SQLException e) {
            System.err.println("Shop schema initialization failed: " + e.getMessage());
        }
    }
}
