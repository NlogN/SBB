package ru.sbb.dao;

import ru.sbb.entity.ScheduleRecord;
import ru.sbb.entity.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import ru.sbb.DateBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class TrainDAOImpl implements TrainDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

//    public TrainDAOImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Override
    public void addTrain(int number, int capacity) {
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

    @Override
    public List<Train> getTrainByNum(int trainNum) {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
        query.setParameter("trNum", trainNum);
        List<Train> list = query.getResultList();
        return list;
    }

    @Override
    public List<Train> getTrainByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
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


    public boolean checkTrainRoute(Train train, java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
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

    @Override
    public List<Train> getTrains() {
        Query query = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr");
        List<Train> trainList = query.getResultList();
        return trainList;
    }
}
