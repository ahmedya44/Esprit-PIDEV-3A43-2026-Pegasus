package com.pegasus.models;

public class LigneCommande {
    private int id;
    private int quantite;
    private float prixUnitaire;
    private Commande commande;
    private Produit produit;

    // Constructeur vide
    public LigneCommande() {}

    // Constructeur sans id (pour INSERT)
    public LigneCommande(int quantite, float prixUnitaire, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
        this.produit = produit;
    }

    // Constructeur complet
    public LigneCommande(int id, int quantite, float prixUnitaire, Commande commande, Produit produit) {
        this.id = id;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
        this.produit = produit;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public float getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(float prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    @Override
    public String toString() {
        return "LigneCommande{id=" + id + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + "}";
    }
}
