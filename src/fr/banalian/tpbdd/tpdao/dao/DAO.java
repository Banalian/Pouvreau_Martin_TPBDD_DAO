package fr.banalian.tpbdd.tpdao.dao;

import fr.banalian.tpbdd.tpdao.ConnectBdd;
import fr.banalian.tpbdd.tpdao.model.Scholarship;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class DAO<T> {

    //variable for a class type
    private final Class<T> type;

    public DAO(Class<T> type) {
        this.type = type;
    }



    public List<T> getAll() {
        return ConnectBdd.getEntityManager().createQuery("SELECT t FROM " + type.getName()  + " t", type).getResultList();
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
