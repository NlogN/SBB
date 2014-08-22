package ru.sbb.dao;


import ru.sbb.SbbEntityManager;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;

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


    @Override
    public void addTicket(Passenger newPassenger, Train train, java.util.Date dateOfRace) {
        EntityTransaction transaction = SbbEntityManager.getInstance().getEntityManager().getTransaction();
        try {
            transaction.begin();

            Ticket newTicket = new Ticket();
            newTicket.setDate(dateOfRace);
            newTicket.setTrain(train);
            newTicket.setPassenger(newPassenger);

           // System.out.println("Date = "+newTicket.getDate().toString());

            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticketList.add(newTicket);
            newPassenger.setTicketList(ticketList);

            SbbEntityManager.getInstance().getEntityManager().persist(newPassenger);
            SbbEntityManager.getInstance().getEntityManager().persist(newTicket);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<Ticket> getTicketByDayAndTrain(Train train, java.util.Date dateOfRace) {
        Query query = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT tic FROM ru.sbb.entity.Ticket tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        return list;
    }



}
