package dao;

import metier.Fonfaron;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FonfaronJDBCDAO {
    private final DbConnectionManager dbManager = DbConnectionManager.getInstance();

    public boolean insert(Fonfaron Fanfaron) {
        Date dateCreation = Date.valueOf(Fanfaron.getDateCreation());
        String query = "INSERT INTO fonfarons (nomfonfaron, email,motdepasse,nom, prenom,genre,contraintealimentaire,createdat,lastconnection,isadmin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NULL, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, Fanfaron.getNomFanfaron());
            ps.setString(2, Fanfaron.getEmail());
            ps.setString(3, Fanfaron.getMotDePasse());
            ps.setString(4, Fanfaron.getNom());
            ps.setString(5, Fanfaron.getPrenom());
            ps.setString(6, Fanfaron.getGenre());
            ps.setString(7, Fanfaron.getContrainte());
            ps.setDate(8, dateCreation);
            ps.setBoolean(9, Fanfaron.isAdmin());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0; // Retourne true si une ligne a été insérée
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du client : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public Fonfaron findByName(String nomFanfaron){
        String sql = "SELECT * FROM fonfarons WHERE nomfonfaron = ?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Fonfaron(
                        rs.getString("nomfonfaron"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fonfaron findByNameMdp(String nomFanfaron, String mdp){
        String sql = "SELECT * FROM fonfarons WHERE nomfonfaron = ? and motdepasse = ?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2,mdp);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fonfaron(
                        rs.getString("nomfonfaron"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(String nomFanfaron) {
        String query = "DELETE FROM Fanfaron WHERE nomfanfaron = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Retourne true si une ligne a été supprimée
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Fonfaron fanfaron) {
        String sql = "UPDATE Fanfarons SET "
                + "nomfanfaron = ?, "
                + "email = ?, "
                + "motdepasse = ?, "
                + "nom = ?, "
                + "prenom = ?, "
                + "genre = ?, "
                + "contraintealimentaire = ?, "
                + "lastconnection = ? "
                + "WHERE nomfanfaron = ?"; // ou id_fanfaron si vous avez un ID

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fanfaron.getNomFanfaron());
            ps.setString(2, fanfaron.getEmail());
            ps.setString(3, fanfaron.getMotDePasse()); // Devrait être déjà hashé
            ps.setString(4, fanfaron.getNom());
            ps.setString(5, fanfaron.getPrenom());
            ps.setString(6, fanfaron.getGenre());
            ps.setString(7, fanfaron.getContrainte());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // Mise à jour lastconnection
            ps.setString(9, fanfaron.getNomFanfaron()); // Pour le WHERE

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du Fanfaron", e);
        }
    }
    public List<Fonfaron> findAll() {
        String query = "SELECT * FROM Fanfaron";
        List<Fonfaron> Fanfarons = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Fanfarons.add(new Fonfaron(
                        rs.getString("nomfanfaron"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des clients : " + e.getMessage());
            e.printStackTrace();
        }
        return Fanfarons;
    }
}
