package metier;

import java.sql.Timestamp;

public class Evenement {
    int id;
    String nom;
    Timestamp horodatage;
    int duree;
    String lieu;
    String description;
    String nomFanfaron;
    public Evenement(int id, String nom, Timestamp horodatage, int duree, String lieu, String description, String nomFanfaron) {
        this.id = id;
        this.nom = nom;
        this.horodatage = horodatage;
        this.duree = duree;
        this.lieu = lieu;
        this.description = description;
        this.nomFanfaron = nomFanfaron;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Timestamp getHorodatage() {
        return horodatage;
    }
public void setHorodatage(Timestamp horodatage) {
    this.horodatage = horodatage;}
    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNomFanfaron() {
        return nomFanfaron;
    }
    public void setNomFanfaron(String nomFanfaron) {
        this.nomFanfaron = nomFanfaron;
    }
}

