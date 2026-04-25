package com.pegasus.models;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private float prix;
    private int stock;
    private String image;
    private String statut; // disponible, rupture, bientot, archive, en_attente, refuse
    private Categorie categorie;

    // Constructeur vide
    public Produit() {}

    // Constructeur sans id (pour INSERT)
    public Produit(String nom, String description, float prix, int stock, String image, String statut, Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.statut = statut;
        this.categorie = categorie;
    }

    // Constructeur complet
    public Produit(int id, String nom, String description, float prix, int stock, String image, String statut, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.statut = statut;
        this.categorie = categorie;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", nom='" + nom + "', prix=" + prix + ", stock=" + stock + ", statut='" + statut + "'}";
    }
}