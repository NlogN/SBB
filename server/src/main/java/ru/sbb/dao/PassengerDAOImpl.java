package ru.sbb.dao;


import ru.sbb.entity.Passenger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class PassengerDAOImpl implements PassengerDAO {
    private EntityManager entityManager;

    public PassengerDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public List<Passenger> getPassengersByTrain(int trainNum) {
        Query query = entityManager.createQuery("SELECT ts.passenger FROM ru.sbb.entity.Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }


}
