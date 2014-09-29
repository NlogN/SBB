package ru.sbb.dao;


import ru.sbb.EntityManagerBean;
import ru.sbb.entity.ScheduleRecord;
import ru.sbb.entity.Station;
import ru.sbb.entity.Train;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class ScheduleRecordDAOImpl implements ScheduleRecordDAO {
    private EntityManager entityManager;

    public void setEntityManager(EntityManagerBean entityManagerBean) {
        this.entityManager = entityManagerBean.getEntityManagerInstance();
    }


    @Override
    public void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) throws StationNotFoundException, TrainNotFoundException {
        Query stationQuery = entityManager.createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        stationQuery.setParameter("stName", stationName);
        List<Station> stationList = stationQuery.getResultList();
        if (stationList.isEmpty()) {
            throw new StationNotFoundException("Station not found!", stationName);
        } else {
            Query trainQuery = entityManager.createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
            trainQuery.setParameter("trNum", trainNumber);
            List<Train> trainList = trainQuery.getResultList();
            if (trainList.isEmpty()) {
                throw new TrainNotFoundException("Train not found!", trainNumber);
            } else {
                EntityTransaction transaction = entityManager.getTransaction();
                try {

                    transaction.begin();
                    Train train = trainList.get(0);
                    Station station = stationList.get(0);
                    ScheduleRecord newSchedule = new ScheduleRecord();

                    List<ScheduleRecord> stationScheduleRecords = station.getScheduleList();
                    if (stationScheduleRecords == null) {
                        stationScheduleRecords = new ArrayList<ScheduleRecord>();
                    }
                    stationScheduleRecords.add(newSchedule);
                    station.setScheduleList(stationScheduleRecords);

                    List<ScheduleRecord> trainScheduleRecords = train.getScheduleList();
                    if (trainScheduleRecords == null) {
                        trainScheduleRecords = new ArrayList<ScheduleRecord>();
                    }
                    trainScheduleRecords.add(newSchedule);
                    train.setScheduleList(trainScheduleRecords);

                    newSchedule.setTrain(train);
                    newSchedule.setStation(station);
                    newSchedule.setOffset(offset);
                    newSchedule.setTime(time);

                    entityManager.persist(newSchedule);
                    transaction.commit();
                } finally {
                    if (transaction.isActive()) transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<ScheduleRecord> getStationScheduleRecords(String stationName) throws StationNotFoundException {
        Query query = entityManager.createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        query.setParameter("stName", stationName);
        List<Station> list = query.getResultList();
        if (list.isEmpty()) {
            throw new StationNotFoundException("Station not found!", stationName);
        } else {
            List<ScheduleRecord> scheduleList = list.get(0).getScheduleList();
            if (scheduleList == null) {
                return new ArrayList<ScheduleRecord>();
            }
            return scheduleList;
        }
    }

    @Override
    public List<ScheduleRecord> findScheduleRecordsByStationNameAndTrain(Train train, String stationName) {
        List<ScheduleRecord> trainScheduleList = train.getScheduleList();
        if (trainScheduleList != null) {
            List<ScheduleRecord> scheduleRecordList = new ArrayList<ScheduleRecord>();
            for (ScheduleRecord schedule : trainScheduleList) {
                if (schedule.getStation().getName().equals(stationName)) {
                    scheduleRecordList.add(schedule);
                }
            }
            return scheduleRecordList;
        } else {
            return new ArrayList<ScheduleRecord>();
        }

    }
}
