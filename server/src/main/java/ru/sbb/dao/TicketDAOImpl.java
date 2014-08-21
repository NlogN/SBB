package ru.sbb.dao;

import ru.sbb.DateBuilder;
import ru.sbb.SbbEntityManager;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.ScheduleRecord;
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
    public boolean buyTicket(int trainNum, String stationName, String name, String surname, java.util.Date birthday, java.util.Date dateOfRace) {
        Query query = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("ru.sbb.entity.Train not found!");
        } else {
            Train train = list.get(0);
            if (checkStartTime(train, stationName) && checkSamePassengerNotReg(train, name, surname, birthday) && checkNotFilledState(train, dateOfRace)) {

                EntityTransaction transaction = SbbEntityManager.getInstance().getEntityManager().getTransaction();
                try {

                    transaction.begin();

                    Passenger newPassenger = new Passenger();
                    newPassenger.setDate(birthday);
                    newPassenger.setName(name);
                    newPassenger.setSurname(surname);

                    Ticket newTicket = new Ticket();
                    newTicket.setDate(dateOfRace);
                    newTicket.setTrain(train);
                    newTicket.setPassenger(newPassenger);

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

            } else {
                return false;
            }

        }

        return true;
    }


    boolean checkStartTime(Train train, String stationName) {
        long currentTime = DateBuilder.getCurrentTime();
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        for (ScheduleRecord schedule : scheduleList) {
            if (schedule.getStation().equals(stationName) && (schedule.getUnixTime() - currentTime) > 0 && (schedule.getUnixTime() - currentTime) < 360) {
                return false;
            }
        }
        return true;
    }

    boolean checkNotFilledState(Train train, java.util.Date dateOfRace) {
        Query query = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT tic FROM ru.sbb.entity.TicketDAO tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        return train.getCapacity() > list.size();
    }

    boolean checkSamePassengerNotReg(Train train, String name, String surname, java.util.Date birthday) {
        List<Ticket> ticketList = train.getTicketList();
        for (Ticket ticket : ticketList) {
            Passenger passenger = ticket.getPassenger();
            if (passenger.getName().equals(name) && passenger.getSurname().equals(surname) && passenger.getDate().equals(birthday)) {
                return false;
            }
        }
        return true;
    }
}
