package entities;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void add(T t) ;
    void update(T t) throws SQLException;
    void delete(T t);

    List<T> getAll();

}

