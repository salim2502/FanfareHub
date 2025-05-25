package dao;

import java.util.List;

public interface CommissionDAO {
    List<String> getAllCommissions();
    List<String> getCommissionsByFanfaron(String nomFanfaron);
    boolean addCommissionToFanfaron(String nomFanfaron, String Commission);
    boolean removeCommissionFromFanfaron(String nomFanfaron, String Commission);
}
