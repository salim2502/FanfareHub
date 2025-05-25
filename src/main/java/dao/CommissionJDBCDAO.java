package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommissionJDBCDAO implements CommissionDAO {

    private final DbConnectionManager dbManager;
    public CommissionJDBCDAO(){
        dbManager = DbConnectionManager.getInstance();
    }

    @Override
    public List<String> getAllCommissions() {
        List<String> commissions = new ArrayList<>();
        String query = "SELECT * FROM groupecommission";
        try(Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                commissions.add(rs.getString("commission"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(commissions);
        return commissions;
    }

    @Override
    public List<String> getCommissionsByFanfaron(String nomFanfaron) {
        List<String> commissions = new ArrayList<>();
        String query = "SELECT commission FROM Impliquer WHERE NomFanfaron = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                commissions.add(rs.getString("commission"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(commissions);
        return commissions;
    }

    @Override
    public boolean addCommissionToFanfaron(String nomFanfaron, String commission) {
        String query = "INSERT INTO Impliquer(NomFanfaron, commission) VALUES(?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2, commission);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCommissionFromFanfaron(String nomFanfaron, String commission) {
        String query = "DELETE FROM Impliquer WHERE NomFanfaron = ? AND commission = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomFanfaron);
            stmt.setString(2, commission);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}