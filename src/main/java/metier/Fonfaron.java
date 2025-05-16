package metier;

import java.time.LocalDate;

public class Fonfaron {
    private String surnom;
    private String email;
    private String motdepasse;
    private String nom;
    private String prenom;
    private String genre;
    private String contrainte;
    private LocalDate dateCreation;
    private Boolean isAdmin;


    public Fonfaron(String nomFanfaron, String email, String motDePasse, String nom, String prenom, String genre, String contrainte) {
        this.surnom = nomFanfaron;
        this.nom = nom;
        this.email = email;
        this.motdepasse = motDePasse;
        this.prenom = prenom;
        this.genre = genre;
        this.contrainte = contrainte;
        this.dateCreation = LocalDate.now();
        this.isAdmin = false;
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

}
