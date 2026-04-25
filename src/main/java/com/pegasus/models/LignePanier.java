package com.pegasus.models;

public class LignePanier {
    private int id;
    private int quantite;
    private float prixUnitaire;
    private Panier panier;
    private Produit produit;

    // Constructeur vide
    public LignePanier() {}

    // Constructeur sans id (pour INSERT)
    public LignePanier(int quantite, float prixUnitaire, Panier panier, Produit produit) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.panier = panier;
        this.produit = produit;
    }

    // Constructeur complet
    public LignePanier(int id, int quantite, float prixUnitaire, Panier panier, Produit produit) {
        this.id = id;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.panier = panier;
        this.produit = produit;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public float getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(float prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    @Override
    public String toString() {
        return "LignePanier{id=" + id + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + "}";
    }
}