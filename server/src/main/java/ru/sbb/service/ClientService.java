package ru.sbb.service;

import ru.sbb.DateBuilder;
import ru.sbb.entity.*;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */
public class ClientService extends Service{
    private static ClientService instance;

    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }


    List<Train> getTrainOnRouteABList(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr");
        List<Train> allTrainList = query.getResultList();
        List<Train> trainFromAToBList = new ArrayList<Train>();
        for (Train train : allTrainList) {
            if (checkTrainRoute(train, lowerBound, upperBound, stationAName, stationBName)) {
                trainFromAToBList.add(train);
            }
        }
        return trainFromAToBList;
    }

    boolean checkNotFilledState(Train train, java.util.Date dateOfRace) {
        Query query = entityManager.createQuery("SELECT tic FROM ru.sbb.entity.TicketDAO tic where tic.train.number =:trNum and tic.date =:day");
        query.setParameter("trNum", train.getNumber());
        query.setParameter("day", dateOfRace);
        List<Ticket> list = query.getResultList();
        return train.getCapacity() > list.size();
    }

    static boolean checkSamePassengerNotReg(Train train, String name, String surname, java.util.Date birthday) {
        List<Ticket> ticketList = train.getTicketList();
        for (Ticket ticket : ticketList) {
            Passenger passenger = ticket.getPassenger();
            if (passenger.getName().equals(name) && passenger.getSurname().equals(surname) && passenger.getDate().equals(birthday)) {
                return false;
            }
        }
        return true;
    }

    static boolean checkTrainRoute(Train train, java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        ScheduleRecord scheduleA = null;
        ScheduleRecord scheduleB = null;
        for (ScheduleRecord schedule : scheduleList) {
            if (scheduleA != null && scheduleB != null) {
                break;
            }
            if (schedule.getStation().getName().equals(stationAName)) {
                scheduleA = schedule;
            }
            if (schedule.getStation().getName().equals(stationBName)) {
                scheduleB = schedule;
            }
        }
        if (scheduleA != null && scheduleB != null) {
            if (scheduleA.getUnixTime() < scheduleB.getUnixTime()) {
                if (DateBuilder.getUnixTime(lowerBound) < scheduleA.getUnixTime() && scheduleB.getUnixTime() < DateBuilder.getUnixTime(upperBound)) {
                    return true;
                }
            }
        }
        return false;
    }



    public String getStationSchedule(String stationName) {
//        Query query = entityManager.createQuery("SELECT ts.train FROM ru.sbb.entity.Station st join st.scheduleList ts where st.name =:stName");
//        query.setParameter("stName", stationName);
//        List<ru.sbb.entity.Train> list = query.getResultList();
//        for (ru.sbb.entity.Train train : list) {
//            System.out.println(train);
//        }
        Query query = entityManager.createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        query.setParameter("stName", stationName);
        List<Station> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("Station not found!");
        } else {
            StringBuffer sb = new StringBuffer();
            List<ScheduleRecord> scheduleList = list.get(0).getScheduleList();
            for (ScheduleRecord schedule : scheduleList) {
                //   System.out.println(schedule.getTrain().getNumber()+" "+schedule.getTime());
                sb.append(schedule.getTrain().getNumber() + " " + schedule.getTime() + "; \n");
            }
            return sb.toString();
        }
        return "";

    }

    boolean buyTicket(int trainNum, String stationName, String name, String surname, java.util.Date birthday, java.util.Date dateOfRace) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("ru.sbb.entity.Train not found!");
        } else {
            Train train = list.get(0);
            if (checkStartTime(train, stationName) && checkSamePassengerNotReg(train, name, surname, birthday) && checkNotFilledState(train, dateOfRace)) {

                EntityTransaction transaction = entityManager.getTransaction();
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

                    entityManager.persist(newPassenger);
                    entityManager.persist(newTicket);
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


    static boolean checkStartTime(Train train, String stationName) {
        long currentTime = DateBuilder.getCurrentTime();
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        for (ScheduleRecord schedule : scheduleList) {
            if (schedule.getStation().equals(stationName) && (schedule.getUnixTime() - currentTime) > 0 && (schedule.getUnixTime() - currentTime) < 360) {
                return false;
            }
        }
        return true;
    }
}
