package ru.sbb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */


public class SbbEntityManager {
    private static SbbEntityManager instance;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static synchronized SbbEntityManager getInstance() {
        if (instance == null) {
            instance = new SbbEntityManager();
        }
        return instance;
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }




    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
