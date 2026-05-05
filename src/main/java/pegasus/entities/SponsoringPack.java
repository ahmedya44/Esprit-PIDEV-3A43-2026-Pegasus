package pegasus.entities;

public class SponsoringPack {
    // Attributs correspondant aux colonnes de l'image
    private int id_pack;
    private String nom_pack;
    private String description;
    private float prix;
    private int id_evenement;
    private int id_sponsor;

    // 1. Constructeur vide
    public SponsoringPack() {
    }

    // 2. Constructeur sans ID (Utilisé pour l'AJOUT/INSERTION)
    public SponsoringPack(String nom_pack, String description, float prix, int id_evenement) {
        this.nom_pack = nom_pack;
        this.description = description;
        this.prix = prix;
        this.id_evenement = id_evenement;
        this.id_sponsor = 0;
    }

    // Ajouté pour compatibilité avec le code existant (ex: tests)
    public SponsoringPack(String nom_pack, String description, float prix) {
        this.nom_pack = nom_pack;
        this.description = description;
        this.prix = prix;
        this.id_evenement = 0; // Valeur par défaut
        this.id_sponsor = 0;
    }

    // 3. Constructeur complet avec ID (Utilisé pour la RÉCUPÉRATION et MODIFICATION)
    public SponsoringPack(int id_pack, String nom_pack, String description, float prix, int id_evenement, int id_sponsor) {
        this.id_pack = id_pack;
        this.nom_pack = nom_pack;
        this.description = description;
        this.prix = prix;
        this.id_evenement = id_evenement;
        this.id_sponsor = id_sponsor;
    }

    // --- GETTERS ET SETTERS ---

    public int getId_pack() {
        return id_pack;
    }

    public void setId_pack(int id_pack) {
        this.id_pack = id_pack;
    }

    public String getNom_pack() {
        return nom_pack;
    }

    public void setNom_pack(String nom_pack) {
        this.nom_pack = nom_pack;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getId_sponsor() {
        return id_sponsor;
    }

    public void setId_sponsor(int id_sponsor) {
        this.id_sponsor = id_sponsor;
    }

    // --- TOSTRING ---
    @Override
    public String toString() {
        return "SponsoringPack{" +
                "id_pack=" + id_pack +
                ", nom_pack='" + nom_pack + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }
}
