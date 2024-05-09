package models;

import java.sql.SQLException;
import java.util.List;

public interface CrudRessource <Ress> {
    public void ajouter(Ress r);
    public void modifier(Ress r);
    public void supprimer(int id) throws SQLException;
    public List<Ressource> afficher();
    public Ressource getById(int id) throws SQLException;
}
