package ru.sbb.dao;

import ru.sbb.SbbEntityManager;
import ru.sbb.entity.Passenger;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class PassengerDAOImpl implements PassengerDAO {

    @Override
    public List<Passenger> getPassengersByTrain(int trainNum) {
        Query query = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT ts.passenger FROM ru.sbb.entity.Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }


}
