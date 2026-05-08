package com.pegasus.entities;

public class Participation {
    private int id;
    private int id_user;
    private int id_evenement;

    public Participation() {}

    public Participation(int id_user, int id_evenement) {
        this.id_user = id_user;
        this.id_evenement = id_evenement;
    }

    public Participation(int id, int id_user, int id_evenement) {
        this.id = id;
        this.id_user = id_user;
        this.id_evenement = id_evenement;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getId_user() { return id_user; }
    public void setId_user(int id_user) { this.id_user = id_user; }
    public int getId_evenement() { return id_evenement; }
    public void setId_evenement(int id_evenement) { this.id_evenement = id_evenement; }
}
