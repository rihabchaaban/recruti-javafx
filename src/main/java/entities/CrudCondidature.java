package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudCondidature<Con> {
    public void ajouter(Con c);
    public void modifier(Con c);
    public void supprimer(int id) throws SQLException;
    public List<Condidature> afficher();
    public Condidature getById(int id) throws SQLException;
}
