package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Scholarship;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DAO<T> {

    //variable for a class type
    private final Class<T> type;

    public DAO(Class<T> type) {
        this.type = type;
    }

    public static void main(String[] args) {
        String[] test = Scholarship.class.getName().split("\\.");
        for (String s : test) {
            System.out.println(s);
        }
    }

    public List<T> getAll() {
        String [] test = type.getName().split("\\.");
        String name = test[test.length - 1];

        return ConnectBdd.getEntityManager().createQuery("SELECT t FROM " + name + " t", type).getResultList();
    }

    public List<T> getAllByColumns(String[] columns, String[] values) {
        String [] test = type.getName().split("\\.");
        String name = test[test.length - 1];

        String query = "SELECT t FROM " + name + " t WHERE ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + " = '" + values[i] + "'";
        }
        return ConnectBdd.getEntityManager().createQuery(query).getResultList();
    }

    public T get(int id) {
        return ConnectBdd.getEntityManager().find(type, id);
    }

    public void persist(T t) {
        ConnectBdd.startTransaction();
        ConnectBdd.getEntityManager().persist(t);
        ConnectBdd.commitTransaction();
    }

    public void update(T t) {
        ConnectBdd.startTransaction();
        ConnectBdd.getEntityManager().merge(t);
        ConnectBdd.commitTransaction();
    }

    public void delete(T t) {
        ConnectBdd.startTransaction();
        ConnectBdd.getEntityManager().remove(t);
        ConnectBdd.commitTransaction();
    }


}
