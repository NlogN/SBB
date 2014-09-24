package ru.sbb.dao;


import ru.sbb.beans.EntityManagerBean;
import ru.sbb.entity.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class StationDAOImpl implements StationDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManagerBean entityManagerBean) {
        this.entityManager = entityManagerBean.getEntityManager1();
    }

//    public void setEntityManager(EntityManagerFactory entityManagerFactory) {
//        //this.entityManager = entityManagerFactory.createEntityManager();
//        this.entityManager = EntityManagerBean.getEntityManager();
//    }

//    public StationDAOImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }



    @Override
    public void addStation(String name) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Station newtStation = new Station();
            newtStation.setName(name);
            entityManager.persist(newtStation);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }
}
