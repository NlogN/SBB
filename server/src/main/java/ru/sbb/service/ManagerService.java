package ru.sbb.service;

import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Train;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */

public class ManagerService {
    private static ManagerService instance;
    private static PassengerDAO passengerDAO = new PassengerDAOImpl();
    private static TrainDAO trainDAO = new TrainDAOImpl();
    private static TicketDAO ticketDAO = new TicketDAOImpl();
    private static StationDAO stationDAO = new StationDAOImpl();
    private static ScheduleRecordDAO scheduleRecordDAO = new ScheduleRecordDAOImpl();

    public static synchronized ManagerService getInstance() {
        if (instance == null) {
            instance = new ManagerService();
        }
        return instance;
    }


    Set<String> getTrainNumberList() {
        List<Train> trainList = trainDAO.getTrains();
        Set<String> trainNumberList = new HashSet<String>();
        for (Train train : trainList) {
            trainNumberList.add(Integer.toString(train.getNumber()));
        }
        return trainNumberList;
    }

    void addTrain(int number, int capacity) {
        trainDAO.addTrain(number,capacity);
    }

    void addStation(String name) {
        stationDAO.addStation(name);
    }

    void addSchedule(String stationName, int trainNumber, Date time, int offset) {
        scheduleRecordDAO.addScheduleRecord(stationName, trainNumber, time, offset);
    }

    public String getPassengersByTrainInfo(int trainNum) {
        List<Passenger> passengerList = passengerDAO.getPassengersByTrain(trainNum);
        StringBuffer sb = new StringBuffer();
        for (Passenger passenger : passengerList) {
            sb.append(passenger.getName() + " " + passenger.getSurname() + " ;\n");
        }
        return sb.toString();
    }


}
