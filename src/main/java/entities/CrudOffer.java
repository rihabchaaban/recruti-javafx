package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudOffer<Off> {
    public void ajouter(Off o);
    public void modifier(Off o);
    public void supprimer(int id) throws SQLException;
    public List<Offer> afficher();
    public Offer getById(int id) throws SQLException;
}
