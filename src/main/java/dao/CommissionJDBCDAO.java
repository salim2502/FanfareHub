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
        Connection conn = null;
        try {
            conn = dbManager.getConnection();
            conn.setAutoCommit(false);
            String deleteQuery = "DELETE FROM Impliquer WHERE NomFanfaron = ? AND commission = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, nomFanfaron);
                deleteStmt.setString(2, commission);
                int rowsAffected = deleteStmt.executeUpdate();
                if ("prestation".equalsIgnoreCase(commission)) {
                    String deleteEventsQuery = "DELETE FROM Evenement WHERE NomFanfaron = ?";
                    try (PreparedStatement deleteEventsStmt = conn.prepareStatement(deleteEventsQuery)) {
                        deleteEventsStmt.setString(1, nomFanfaron);
                        deleteEventsStmt.executeUpdate();
                    }
                    String deleteInscriptionsQuery = "DELETE FROM Sinscrire WHERE NomFanfaron = ?";
                    try (PreparedStatement deleteInscriptionsStmt = conn.prepareStatement(deleteInscriptionsQuery)) {
                        deleteInscriptionsStmt.setString(1, nomFanfaron);
                        deleteInscriptionsStmt.executeUpdate();
                    }
                }
                conn.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean addCommission(String commission) {
        String query = "INSERT INTO GroupeCommission(commission) VALUES(?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, commission);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCommission(String commission) {
        Connection conn = null;
        try {
            conn = dbManager.getConnection();
            conn.setAutoCommit(false);
            String deleteImpliquerQuery = "DELETE FROM Impliquer WHERE commission = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteImpliquerQuery)) {
                stmt.setString(1, commission);
                stmt.executeUpdate();
            }
            String deleteCommissionQuery = "DELETE FROM GroupeCommission WHERE commission = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteCommissionQuery)) {
                stmt.setString(1, commission);
                int affectedRows = stmt.executeUpdate();

                conn.commit();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}