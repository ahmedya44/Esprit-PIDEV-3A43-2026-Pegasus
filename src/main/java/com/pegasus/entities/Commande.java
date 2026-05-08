package com.pegasus.entities;

import java.time.LocalDateTime;

public class Commande {
    private int id;
    private LocalDateTime dateCommande;
    private String statut;
    private float total;

    public Commande() {
    }

    public Commande(LocalDateTime dateCommande, String statut, float total) {
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.total = total;
    }

    public Commande(int id, LocalDateTime dateCommande, String statut, float total) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", dateCommande=" + dateCommande
                + ", statut='" + statut + "', total=" + total + "}";
    }
}
