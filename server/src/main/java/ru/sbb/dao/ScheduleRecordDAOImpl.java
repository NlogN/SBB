package ru.sbb.dao;

import ru.sbb.SbbEntityManager;
import ru.sbb.entity.ScheduleRecord;
import ru.sbb.entity.Station;
import ru.sbb.entity.Train;

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

    @Override
    public void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) {
        Query stationQuery = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        stationQuery.setParameter("stName", stationName);
        List<Station> stationList = stationQuery.getResultList();
        if (stationList.isEmpty()) {
            System.out.println("Station not found!");
            return;
        }

        Query trainQuery = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT tr FROM ru.sbb.entity.Train tr where tr.number =:trNum");
        trainQuery.setParameter("trNum", trainNumber);
        List<Train> trainList = trainQuery.getResultList();
        if (trainList.isEmpty()) {
            System.out.println("Train not found!");
            return;
        }

        EntityTransaction transaction = SbbEntityManager.getInstance().getEntityManager().getTransaction();
        try {
            transaction.begin();
            ScheduleRecord newSchedule = new ScheduleRecord();
            newSchedule.setTrain(trainList.get(0));
            newSchedule.setStation(stationList.get(0));
            newSchedule.setOffset(offset);
            newSchedule.setTime(time);
            SbbEntityManager.getInstance().getEntityManager().persist(newSchedule);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
        }
    }


    public List<ScheduleRecord> getStationScheduleRecords(String stationName) {
        Query query = SbbEntityManager.getInstance().getEntityManager().createQuery("SELECT st FROM ru.sbb.entity.Station st where st.name =:stName");
        query.setParameter("stName", stationName);
        List<Station> list = query.getResultList();
        if (list.isEmpty()) {
            System.out.println("Station not found!");
        } else {
            List<ScheduleRecord> scheduleList = list.get(0).getScheduleList();
            return scheduleList;
        }
        return new ArrayList<ScheduleRecord>();

    }
}
