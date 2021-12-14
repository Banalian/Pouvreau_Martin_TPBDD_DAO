package fr.banalian.tpbdd.tpdao.dao;

import java.util.ArrayList;

public interface DAO<T> {

    ArrayList<T> getAll();
    T get(int id);
    boolean update(T t);
    boolean delete(int id);
    boolean add(T t);

}
