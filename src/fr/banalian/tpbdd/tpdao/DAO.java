package fr.banalian.tpbdd.tpdao;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();
    T get(int id);
    boolean update(T t);
    boolean delete(int id);
    boolean add(T t);

}
