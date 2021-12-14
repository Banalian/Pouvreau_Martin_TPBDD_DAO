package fr.banalian.tpbdd.tpdao.dao;

import java.util.ArrayList;

public interface DAO<T> {

    /**
     * get all the <T> in the database
     * @return an ArrayList of <T>
     */
    ArrayList<T> getAll();

    /**
     * update the <T> in the database
     * @param t the <T> to update
     * @return true if the update is successful, false otherwise
     */
    boolean update(T t);

    /**
     * delete the <T> in the database
     * @param id the id of the <T> to delete
     * @return true if the delete is successful, false otherwise
     */
    boolean delete(int id);

    /**
     * add a new <T> in the database
     * @param t the <T> to add
     * @return true if the add is successful, false otherwise
     */
    boolean add(T t);

}
