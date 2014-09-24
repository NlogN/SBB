package ru.sbb.beans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Admin on 24.09.2014.
 */
public class EntityManagerBean {
    private  EntityManager entityManager;

    public void setEntityManager(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager1() {
        return entityManager;
    }

}
