package ru.sbb.service;

import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Train;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;

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
    private PassengerDAO passengerDAO;
    private TrainDAO trainDAO;
    private StationDAO stationDAO;
    private ScheduleRecordDAO scheduleRecordDAO;


    public ManagerService(PassengerDAO passengerDAO, TrainDAO trainDAO, StationDAO stationDAO, ScheduleRecordDAO scheduleRecordDAO) {
        this.scheduleRecordDAO = scheduleRecordDAO;
        this.passengerDAO = passengerDAO;
        this.trainDAO = trainDAO;
        this.stationDAO = stationDAO;
    }


    public String getTrainNumbers() {
        List<Train> trainList = trainDAO.getTrains();
        if (trainList.isEmpty()) {
            return "no data";
        } else {
            Set<String> trainNumberSet = new HashSet<String>();
            for (Train train : trainList) {
                trainNumberSet.add(Integer.toString(train.getNumber()));
            }
            StringBuffer sb = new StringBuffer();
            for (String num : trainNumberSet) {
                sb.append("train " + num + " ;\n");
            }
            return sb.toString();
        }
    }

    public void addTrain(int number, int capacity) {
        trainDAO.addTrain(number, capacity);
    }

    public void addStation(String name) {
        stationDAO.addStation(name);
    }

    public void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) throws StationNotFoundException, TrainNotFoundException {
        scheduleRecordDAO.addScheduleRecord(stationName, trainNumber, time, offset);
    }

    public String getPassengersInfoByTrainNum(int trainNum) {
        List<Passenger> passengerList = passengerDAO.getPassengersByTrain(trainNum);
        if (passengerList.isEmpty()) {
            return "no data";
        } else {
            StringBuffer sb = new StringBuffer();
            for (Passenger passenger : passengerList) {
                sb.append(passenger.getName() + " " + passenger.getSurname() + " ;\n");
            }
            return sb.toString();
        }
    }


}
