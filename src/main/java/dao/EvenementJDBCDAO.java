package dao;

import metier.Evenement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvenementJDBCDAO implements EvenementDAO {

    private final DbConnectionManager dbManager;

    public EvenementJDBCDAO() {
        dbManager = DbConnectionManager.getInstance();
    }

    @Override
    public Evenement findById(int id) {
        String query = "SELECT * FROM Evenement WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evenement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getTimestamp("horodatage"),
                        rs.getInt("duree"),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("Nomfanfaron")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Evenement> findAll() {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM Evenement ORDER BY horodatage";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                evenements.add(new Evenement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getTimestamp("horodatage"),
                        rs.getInt("duree"),
                        rs.getString("lieu"),
                        rs.getString("description"),
                        rs.getString("NomFanfaron")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(evenements);
        return evenements;
    }

    @Override
    public boolean insert(Evenement evenement) {
        String query = "INSERT INTO Evenement(nom, horodatage, duree, lieu, description, Nomfanfaron) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, evenement.getNom());
            stmt.setTimestamp(2, evenement.getHorodatage());
            stmt.setInt(3, evenement.getDuree());
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setString(6, evenement.getNomFanfaron());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        evenement.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Evenement evenement) {
        String query = "UPDATE Evenement SET nom = ?, horodatage = ?, duree = ?, lieu = ?, description = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, evenement.getNom());
            stmt.setTimestamp(2, evenement.getHorodatage());
            stmt.setInt(3, evenement.getDuree());
            stmt.setString(4, evenement.getLieu());
            stmt.setString(5, evenement.getDescription());
            stmt.setInt(6, evenement.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Map<String, String>> getInscriptionsForEvent(int eventId) throws SQLException {
        List<Map<String, String>> inscriptions = new ArrayList<>();
        String query = "SELECT s.Nomfanfaron, s.nomPupitre, s.statut, f.nom, f.prenom " +
                "FROM Sinscrire s JOIN Fanfarons f ON s.Nomfanfaron = f.Nomfanfaron " +
                "WHERE s.id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, String> inscription = new HashMap<>();
                inscription.put("nomFanfaron", rs.getString("Nomfanfaron"));
                inscription.put("pupitre", rs.getString("nomPupitre"));
                inscription.put("statut", rs.getString("statut"));
                inscription.put("nomComplet", rs.getString("prenom") + " " + rs.getString("nom"));
                inscriptions.add(inscription);
            }
        }
        return inscriptions;
    }
    public boolean inscrireFanfaron(String nomFanfaron, int eventId, String pupitre, String statut) {
        String query = "INSERT INTO Sinscrire(Nomfanfaron, id, nomPupitre, statut) VALUES(?, ?, ?, ?) " +
                "ON CONFLICT (Nomfanfaron, id) DO UPDATE SET nomPupitre = EXCLUDED.nomPupitre, statut = EXCLUDED.statut";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setInt(2, eventId);
            stmt.setString(3, pupitre);
            stmt.setString(4, statut);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean desinscrireFanfaron(String nomFanfaron, int eventId) {
        String query = "DELETE FROM Sinscrire WHERE Nomfanfaron = ? AND id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setInt(2, eventId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Evenement WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}