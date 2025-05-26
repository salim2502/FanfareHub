package metier;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Fanfaron {
    private String surnom;
    private String email;
    private String motdepasse;
    private String nom;
    private String prenom;
    private String genre;
    private String contrainte;
    private LocalDate dateCreation;
    private Timestamp dateConnection;
    private Boolean isAdmin;


    public Fanfaron(String nomFanfaron, String email, String motDePasse, String nom, String prenom, String genre, String contrainte, boolean admin) {
        this.surnom = nomFanfaron;
        this.nom = nom;
        this.email = email;
        this.motdepasse = motDePasse;
        this.prenom = prenom;
        this.genre = genre;
        this.contrainte = contrainte;
        this.dateCreation = LocalDate.now();
        this.isAdmin = admin;
    }

    // Getters et Setters
    public String getNomFanfaron() { return surnom; }
    public void setNomFanfaron(String surnom) { this.surnom = surnom; }

    public String getPrenom() {
        return prenom;
    }

    public String getNom(){
        return nom;
    }

    public String getGenre() {
        return genre;
    }

    public String getContrainte() {
        return contrainte;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motdepasse; }
    public void setMotDePasse(String motDePasse) { this.motdepasse = motDePasse; }

    public void setAdmin(Boolean isAdmin){this.isAdmin = isAdmin;}
    public void setDateConnection(Timestamp dateConnection) { this.dateConnection = dateConnection; }
    public Timestamp getDateConnection() { return dateConnection; }

    @Override
    public String toString() {
        return nom + " " + prenom + " " + genre + " " + contrainte + " " + dateCreation + " " + isAdmin;
    }

}
