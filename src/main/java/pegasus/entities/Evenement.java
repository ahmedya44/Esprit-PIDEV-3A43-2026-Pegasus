package pegasus.entities;

public class Evenement {
    private int id;
    private String titre;
    private String date;
    private String heure;
    private String lieu;
    private String description;
    private String image;
    private int capacite_max;
    private float prix;
    private String statut;

    public Evenement() {
    }

    public Evenement(String titre, String date, String heure, String lieu, String description,
                     String image, int capacite_max, float prix, String statut) {
        this.titre = titre;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.description = description;
        this.image = image;
        this.capacite_max = capacite_max;
        this.prix = prix;
        this.statut = statut;
    }

    public Evenement(int id, String titre, String date, String heure, String lieu, String description,
                     String image, int capacite_max, float prix, String statut) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.description = description;
        this.image = image;
        this.capacite_max = capacite_max;
        this.prix = prix;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getCapacite_max() { return capacite_max; }
    public void setCapacite_max(int capacite_max) { this.capacite_max = capacite_max; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", capacite_max=" + capacite_max +
                ", prix=" + prix +
                ", statut='" + statut + '\'' +
                '}';
    }
}