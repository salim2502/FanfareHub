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
        System.out.println(pupitres);
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
        System.out.println(pupitres);
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
}