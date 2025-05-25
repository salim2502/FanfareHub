package dao;

import metier.Fanfaron;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FanfaronJDBCDAO {
    private final DbConnectionManager dbManager = DbConnectionManager.getInstance();

    public boolean insert(Fanfaron Fanfaron) {
        Date dateCreation = Date.valueOf(Fanfaron.getDateCreation());
        String query = "INSERT INTO fanfarons (nomfanfaron, email,motdepasse,nom, prenom,genre,contraintealimentaire,createdat,lastconnection,isadmin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NULL, ?)";
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
    public Fanfaron findByName(String nomFanfaron){
        String sql = "SELECT * FROM fanfarons WHERE nomfanfaron = ?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Fanfaron(
                        rs.getString("nomfanfaron"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire"),
                        rs.getBoolean("isadmin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fanfaron findByNameMdp(String nomFanfaron, String mdp){
        String sql = "SELECT * FROM fanfarons WHERE nomfanfaron = ? and motdepasse = ?";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2,mdp);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fanfaron(
                        rs.getString("nomfanfaron"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire"),
                        rs.getBoolean("isadmin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(String nomFanfaron) {
        String query = "DELETE FROM Fanfarons WHERE nomfanfaron = ?";
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

    public boolean update(Fanfaron fanfaron) {
        String sql = "UPDATE Fanfarons SET "
                + "nomfanfaron = ?, "
                + "email = ?, "
                + "motdepasse = ?, "
                + "nom = ?, "
                + "prenom = ?, "
                + "genre = ?, "
                + "contraintealimentaire = ?, "
                + "lastconnection = ? "
                + "WHERE nomfanfaron = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fanfaron.getNomFanfaron());
            ps.setString(2, fanfaron.getEmail());
            ps.setString(3, fanfaron.getMotDePasse());
            ps.setString(4, fanfaron.getNom());
            ps.setString(5, fanfaron.getPrenom());
            ps.setString(6, fanfaron.getGenre());
            ps.setString(7, fanfaron.getContrainte());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, fanfaron.getNomFanfaron());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du Fanfaron", e);
        }
    }
    public List<Fanfaron> findAll() {
        String query = "SELECT * FROM Fanfarons";
        List<Fanfaron> Fanfarons = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Fanfarons.add(new Fanfaron(
                        rs.getString("nomfanfaron"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("contraintealimentaire"),
                        rs.getBoolean("isadmin")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des clients : " + e.getMessage());
            e.printStackTrace();
        }
        return Fanfarons;
    }
    public void editAdmin(String nomFanfaron) throws SQLException {
        String sql = "UPDATE fanfarons SET isadmin = NOT isadmin WHERE nomfanfaron = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, nomFanfaron);
            stmt.executeUpdate();
        }
    }
}
