package ru.sbb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class EntityManagerBean {
    private  EntityManager entityManager;

    public void setEntityManager(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManagerInstance() {
        return entityManager;
    }

}
