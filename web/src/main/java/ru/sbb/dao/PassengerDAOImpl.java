package ru.sbb.dao;


import ru.sbb.EntityManagerBean;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;



/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */


public class PassengerDAOImpl implements PassengerDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManagerBean entityManagerBean) {
        this.entityManager = entityManagerBean.getEntityManagerInstance();
    }

    @Override
    public List<Passenger> getPassengersByTrain(int trainNum) {
        Query query = entityManager.createQuery("SELECT ts.passenger FROM ru.sbb.entity.Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }

    @Override
    public List<Passenger> getPassengersByInfo(Train train, String name, String surname, java.util.Date birthday) {
        List<Ticket> ticketList = train.getTicketList();
        List<Passenger> passengerList = new ArrayList<Passenger>();
        if (ticketList != null) {
            for (Ticket ticket : ticketList) {
                Passenger passenger = ticket.getPassenger();
                if (passenger.getName().equals(name) && passenger.getSurname().equals(surname) && passenger.getDate().equals(birthday)) {
                    passengerList.add(passenger);
                }
            }
        }
        return passengerList;
    }


}
