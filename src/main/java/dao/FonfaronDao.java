package dao;

import metier.Fonfaron;

import java.util.List;

public interface FonfaronDao {
    boolean insert(Fonfaron joueur);
    Fonfaron findByName(String nomFanfaron);
    boolean delete(int id);
    boolean update(Fonfaron joueur);
    List<Fonfaron> findAll();
    Fonfaron findByNameMdp(String nomFanfaron, String mdp);
}
