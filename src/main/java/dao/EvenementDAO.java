package dao;

import metier.Evenement;
import java.util.List;

public interface EvenementDAO {
    Evenement findById(int id);
    List<Evenement> findAll();
    boolean insert(Evenement evenement);
    boolean update(Evenement evenement);
    boolean delete(int id);
}