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
    private static SessionFactory sessionFactory;

    public static void initConnection() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("erasmus");
        em = emf.createEntityManager();

    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration
                .buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static void closeConnection() {
        em.close();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }


}
