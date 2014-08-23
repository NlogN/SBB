package ru.sbb.dao;

import ru.sbb.entity.ScheduleRecord;
import ru.sbb.exception.StationNotFoundExeption;
import ru.sbb.exception.TrainNotFoundExeption;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface ScheduleRecordDAO {
    void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) throws StationNotFoundExeption, TrainNotFoundExeption;

    List<ScheduleRecord> getStationScheduleRecords(String stationName) throws StationNotFoundExeption;
}
