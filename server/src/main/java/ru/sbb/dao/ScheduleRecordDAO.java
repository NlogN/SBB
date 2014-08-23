package ru.sbb.dao;

import ru.sbb.entity.ScheduleRecord;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface ScheduleRecordDAO {
    void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) throws StationNotFoundException, TrainNotFoundException;

    List<ScheduleRecord> getStationScheduleRecords(String stationName) throws StationNotFoundException;
}
