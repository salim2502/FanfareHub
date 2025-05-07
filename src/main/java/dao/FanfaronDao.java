package dao;

import model.Fanfaron;

import java.util.List;

public interface FanfaronDao {
    boolean create(Fanfaron joueur);
    Fanfaron findById(int jno);
    boolean delete(int id);
    boolean update(Fanfaron joueur);
    List<Fanfaron> findAll();
}
