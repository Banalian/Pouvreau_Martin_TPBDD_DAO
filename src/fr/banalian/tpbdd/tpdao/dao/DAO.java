package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;


import java.util.ArrayList;

import java.util.List;

public class DAO<T> {

    //variable for a class type
    private final Class<T> type;

    public DAO(Class<T> type) {
        this.type = type;
    }

    public List<T> getAll() {
        String name = type.getSimpleName();

        return ConnectBdd.getEntityManager().createQuery("SELECT t FROM " + name + " t", type).getResultList();
    }

    public List<T> getAllByColumns(String[] columns, ArrayList<Object> values) {
        String name = type.getSimpleName();

        String query = "SELECT t FROM " + name + " t WHERE ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + " = '" + values.get(i) + "'";
            if (i != columns.length - 1) {
                query += " AND ";
            }
        }
        return ConnectBdd.getEntityManager().createQuery(query).getResultList();
    }

    public T get(Object id) {
        return ConnectBdd.getEntityManager().find(type, id);
    }

    public Object getColumn(int id, String column){
        String[] string = type.getName().split("\\.");
        return ConnectBdd.getEntityManager().createQuery("SELECT " + column + " FROM " + string[string.length-1] + " WHERE id =" + id).getSingleResult();
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
