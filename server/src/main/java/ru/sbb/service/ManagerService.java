package ru.sbb.service;

import ru.sbb.entity.Passenger;
import ru.sbb.entity.Schedule;
import ru.sbb.entity.Station;
import ru.sbb.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */

public class ManagerService extends Service{
    private static ManagerService instance;

    public static synchronized ManagerService getInstance() {
        if (instance == null) {
            instance = new ManagerService();
        }
        return instance;
    }


    List<Train> getTrainList() {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr");
        List<Train> trainList = query.getResultList();
        return trainList;
    }


    Set<String> getTrainNumberList(EntityManager entityManager) {
        List<Train> trainList = getTrainList();
        Set<String> trainNumberList = new HashSet<String>();
        for (Train train : trainList) {
            trainNumberList.add(Integer.toString(train.getNumber()));
        }
        return trainNumberList;
    }

    void addTrain(int number, int capacity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Train newtTrain = new Train();
            newtTrain.setNumber(number);
            newtTrain.setCapacity(capacity);
            entityManager.persist(newtTrain);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }

    void addStation(String name) {
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

    List<Passenger> getPassengersByTrain(int trainNum) {
        Query query = entityManager.createQuery("SELECT ts.passenger FROM ru.sbb.entity.Train tr join tr.ticketList ts where tr.number =:numb");
        query.setParameter("numb", trainNum);
        List<Passenger> passengerList = query.getResultList();
        return passengerList;
    }

    public String getPassengersByTrainInfo(int trainNum) {
        List<Passenger> passengerList = getPassengersByTrain(trainNum);
        StringBuffer sb = new StringBuffer();
        for (Passenger passenger : passengerList) {
            sb.append(passenger.getName() + " " + passenger.getSurname() + " ;\n");
        }
        return sb.toString();
    }

    void addSchedule(String stationName, int trainNumber, Date time, int offset) {
        Query stationQuery = entityManager.createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        stationQuery.setParameter("stName", stationName);
        List<Station> stationList = stationQuery.getResultList();
        if (stationList.isEmpty()) {
            System.out.println("ru.sbb.entity.Station not found!");
            return;
        }

        Query trainQuery = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
        trainQuery.setParameter("trNum", trainNumber);
        List<Train> trainList = trainQuery.getResultList();
        if (trainList.isEmpty()) {
            System.out.println("ru.sbb.entity.Train not found!");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Schedule newSchedule = new Schedule();
            newSchedule.setTrain(trainList.get(0));
            newSchedule.setStation(stationList.get(0));
            newSchedule.setOffset(offset);
            newSchedule.setTime(time);
            entityManager.persist(newSchedule);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }
}
