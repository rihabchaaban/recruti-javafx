package pidevclient.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void create(T obj) throws SQLException;
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    void update(T obj) throws SQLException;
    void delete(int id) throws SQLException;
}