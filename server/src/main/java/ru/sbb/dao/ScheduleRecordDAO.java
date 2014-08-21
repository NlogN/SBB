package ru.sbb.dao;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface ScheduleRecordDAO {
    void addSchedule(String stationName, int trainNumber, Date time, int offset);
}
