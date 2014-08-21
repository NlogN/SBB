package ru.sbb.dao;

import ru.sbb.SbbEntityManager;
import ru.sbb.entity.Station;

import javax.persistence.EntityTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class StationDAOImpl implements StationDAO {

    @Override
    public void addStation(String name) {
        EntityTransaction transaction = SbbEntityManager.getInstance().getEntityManager().getTransaction();
        try {
            transaction.begin();
            Station newtStation = new Station();
            newtStation.setName(name);
            SbbEntityManager.getInstance().getEntityManager().persist(newtStation);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }
}
