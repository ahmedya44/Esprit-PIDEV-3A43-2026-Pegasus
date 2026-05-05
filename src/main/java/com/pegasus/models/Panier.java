package com.pegasus.models;

import java.time.LocalDateTime;

public class Panier {
    private int id;
    private LocalDateTime dateCreation;
    private float total;

    // Constructeur vide
    public Panier() {}

    // Constructeur sans id (pour INSERT)
    public Panier(LocalDateTime dateCreation, float total) {
        this.dateCreation = dateCreation;
        this.total = total;
    }

    // Constructeur complet
    public Panier(int id, LocalDateTime dateCreation, float total) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.total = total;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    @Override
    public String toString() {
        return "Panier{id=" + id + ", dateCreation=" + dateCreation + ", total=" + total + "}";
    }
}