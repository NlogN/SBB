package ru.sbb.dao;


import ru.sbb.EntityManagerBean;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class TicketDAOImpl implements TicketDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManagerBean entityManagerBean) {
        this.entityManager = entityManagerBean.getEntityManagerInstance();
    }



    @Override
    public void addTicket(Passenger newPassenger, Train train, java.util.Date dateOfRace) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Ticket newTicket = new Ticket();
            newTicket.setDate(dateOfRace);
            newTicket.setTrain(train);
            newTicket.setPassenger(newPassenger);

            List<Ticket> trainTicketList = train.getTicketList();
            if (trainTicketList == null) {
                trainTicketList = new ArrayList<Ticket>();
            }
            trainTicketList.add(newTicket);
            train.setTicketList(trainTicketList);

            newTicket.setTrain(train);

            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticketList.add(newTicket);
            newPassenger.setTicketList(ticketList);

            entityManager.persist(newPassenger);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<Ticket> getTicketsByDayAndTrain(Train train, java.util.Date dateOfRace) {
        Query query = entityManager.createQuery("SELECT tic FROM ru.sbb.entity.Ticket tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        return list;
    }


}
