package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PupitreJDBCDAO implements PupitreDAO {

    private final DbConnectionManager dbManager;
    public PupitreJDBCDAO(){
        dbManager = DbConnectionManager.getInstance();
    }

    @Override
    public List<String> getAllPupitres() {
        List<String> pupitres = new ArrayList<>();
        String query = "SELECT * FROM pupitre";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pupitres.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pupitres;
    }

    @Override
    public List<String> getPupitresByFanfaron(String nomFanfaron) {
        List<String> pupitres = new ArrayList<>();
        String query = "SELECT nom FROM Appartenir WHERE NomFanfaron = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pupitres.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pupitres;
    }

    @Override
    public boolean addPupitreToFanfaron(String nomFanfaron, String pupitre) {
        String query = "INSERT INTO Appartenir(NomFanfaron, nom) VALUES(?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2, pupitre);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removePupitreFromFanfaron(String nomFanfaron, String pupitre) {
        String query = "DELETE FROM Appartenir WHERE NomFanfaron = ? AND nom = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2, pupitre);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean addPupitre(String nomPupitre) {
        String query = "INSERT INTO Pupitre(nom) VALUES(?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomPupitre);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean removePupitre(String nomPupitre) {
        Connection conn = null;
        try {
            conn = dbManager.getConnection();
            conn.setAutoCommit(false);

            String deleteSinscrireQuery = "DELETE FROM sinscrire WHERE nomPupitre = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSinscrireQuery)) {
                stmt.setString(1, nomPupitre);
                stmt.executeUpdate();
            }

            String deleteAppartenirQuery = "DELETE FROM appartenir WHERE nom = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteAppartenirQuery)) {
                stmt.setString(1, nomPupitre);
                stmt.executeUpdate();
            }
            String deletePupitreQuery = "DELETE FROM pupitre WHERE nom = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deletePupitreQuery)) {
                stmt.setString(1, nomPupitre);
                int affectedRows = stmt.executeUpdate();
                conn.commit();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Annuler en cas d'erreur
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Rétablir le mode auto-commit
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}