package fr.banalian.tpbdd.tpdao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConnectBdd {

    private static EntityManager em;

    public static void initConnection() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("erasmus");
        em = emf.createEntityManager();

    }

    public static EntityManager getEntityManager() {
        return em;
    }

    public static void startTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commitTransaction() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }

    public static void closeConnection() {
        em.close();
    }



}
