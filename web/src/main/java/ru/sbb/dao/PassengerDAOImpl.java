package ru.sbb.dao;


import ru.sbb.entity.Passenger;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    public void setEntityManager(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

//    public PassengerDAOImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }


    @Override
    public List<Passenger> getPassengersByTrain(int trainNum) {

        Query query = entityManager.createQuery("SELECT ts.passenger FROM ru.sbb.entity.Train tr join tr.ticketList ts where tr.number =:numb");
       // System.out.println(entityManager.isOpen());

        query.setParameter("numb", trainNum);
     //   System.out.println(entityManager.isOpen());
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
