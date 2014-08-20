package ru.sbb.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public abstract class Service {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
    public EntityManager entityManager = entityManagerFactory.createEntityManager();

    static Logger log = Logger.getLogger(Service.class.getName());

    public void close(){
        entityManager.close();
        entityManagerFactory.close();
    }
}
