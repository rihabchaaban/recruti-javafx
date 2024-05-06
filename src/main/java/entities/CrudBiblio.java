package entities;

import java.sql.SQLException;
import java.util.List;

public interface CrudBiblio<Bib> {
    public void ajouter(Bib b);
    public void modifier(Bib b);
    public void supprimer(int id) throws SQLException;
    public List<Biblio> afficher();
    public Biblio getById(int id) throws SQLException;
}

