package ru.sbb.dao;


import ru.sbb.EntityManagerBean;
import ru.sbb.entity.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class StationDAOImpl implements StationDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManagerBean entityManagerBean) {
        this.entityManager = entityManagerBean.getEntityManagerInstance();
    }



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
