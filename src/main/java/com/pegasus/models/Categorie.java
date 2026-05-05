package com.pegasus.models;



public class Categorie {
    private int id;
    private String nom;
    private String description;

    // Constructeur vide
    public Categorie() {}

    // Constructeur sans id (pour INSERT)
    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    // Constructeur complet
    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return nom;
    }
}