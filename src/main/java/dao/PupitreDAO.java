package dao;

import java.util.List;

public interface PupitreDAO {
    List<String> getAllPupitres();
    List<String> getPupitresByFanfaron(String nomFanfaron);
    boolean addPupitreToFanfaron(String nomFanfaron, String pupitre);
    boolean removePupitreFromFanfaron(String nomFanfaron, String pupitre);
}
