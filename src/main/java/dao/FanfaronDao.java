package dao;

import metier.Fanfaron;

import java.util.List;

public interface FanfaronDao {
    boolean insert(Fanfaron joueur);
    Fanfaron findByName(String nomFanfaron);
    boolean delete(int id);
    boolean update(Fanfaron joueur);
    List<Fanfaron> findAll();
    Fanfaron findByNameMdp(String nomFanfaron, String mdp);
}
