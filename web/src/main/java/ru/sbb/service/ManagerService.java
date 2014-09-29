package ru.sbb.service;

import org.apache.log4j.Logger;
import ru.sbb.DateBuilder;
import ru.sbb.dto.PassengerRecord;
import ru.sbb.dao.PassengerDAO;
import ru.sbb.dao.ScheduleRecordDAO;
import ru.sbb.dao.StationDAO;
import ru.sbb.dao.TrainDAO;
import ru.sbb.dto.TrainRecord;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Train;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;

import java.util.*;

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

    private static Logger logger = Logger.getLogger(ClientService.class);

    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO=stationDAO;
    }

    public void setScheduleRecordDAO(ScheduleRecordDAO scheduleRecordDAO) {
        this.scheduleRecordDAO = scheduleRecordDAO;
    }

    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    public void setPassengerDAO(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }


    public List<TrainRecord> getTrains() {
        logger.info("getTrains request");
        List<Train> trainList = trainDAO.getTrains();
        List<TrainRecord> trains = new ArrayList<TrainRecord>();
        for (Train train : trainList) {
            TrainRecord newTrainRecord = new TrainRecord();
            newTrainRecord.setNumber(train.getNumber());
            newTrainRecord.setCapacity(train.getCapacity());
            trains.add(newTrainRecord);
        }
        return trains;
    }

    public void addTrain(int number, int capacity) {
        logger.info("addTrain request with param: " + "train: " + number);
        trainDAO.addTrain(number, capacity);
    }

    public void addStation(String name) {
        logger.info("addStation request with param: " + "station name: " + name);
        stationDAO.addStation(name);
    }

    public void addScheduleRecord(String stationName, int trainNumber, Date time, int offset) throws StationNotFoundException, TrainNotFoundException {
        logger.info("addScheduleRecord request with param: " + "station name: " + stationName);
        scheduleRecordDAO.addScheduleRecord(stationName, trainNumber, time, offset);
    }

    public List<PassengerRecord> getPassengersInfoByTrainNum(int trainNum) {
        logger.info("getPassengersInfoByTrainNum request with param: " + "train: " + trainNum);
        List<Passenger> passengerList = passengerDAO.getPassengersByTrain(trainNum);
        List<PassengerRecord> passengers = new ArrayList<PassengerRecord>();
        for (Passenger passenger:passengerList){
            PassengerRecord passengerRecord = new PassengerRecord();
            passengerRecord.setName(passenger.getName());
            passengerRecord.setSurname(passenger.getSurname());
            String birthDate = DateBuilder.dateToString(passenger.getDate());
            passengerRecord.setDate(birthDate);
            passengers.add(passengerRecord);
        }
        return passengers;
    }


}
