package services;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface IService1 <T>{
    //CRUD
    void add(T t);
    void update (T t);
    void delete (T t);
    List<T> getAll();
    //  T getOne(int id);

}
