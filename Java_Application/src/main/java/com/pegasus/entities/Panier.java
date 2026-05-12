package com.pegasus.entities;

import java.time.LocalDateTime;

public class Panier {
    private int id;
    private LocalDateTime dateCreation;
    private float total;

    public Panier() {
    }

    public Panier(LocalDateTime dateCreation, float total) {
        this.dateCreation = dateCreation;
        this.total = total;
    }

    public Panier(int id, LocalDateTime dateCreation, float total) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Panier{id=" + id + ", dateCreation=" + dateCreation
                + ", total=" + total + "}";
    }
}
