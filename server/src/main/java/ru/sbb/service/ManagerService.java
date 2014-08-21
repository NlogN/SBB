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


    public String getTrainNumbers() {
        List<Train> trainList = trainDAO.getTrains();
        if(trainList.isEmpty()){
            return "no data";
        }else{
            Set<String> trainNumberSet = new HashSet<String>();
            for (Train train : trainList) {
                trainNumberSet.add(Integer.toString(train.getNumber()));
            }
            StringBuffer sb = new StringBuffer();
            for (String num : trainNumberSet) {
                sb.append(num + " ;\n");
            }
            return sb.toString();
        }
    }

    public String addTrain(int number, int capacity) {
        trainDAO.addTrain(number,capacity);
        return "train added";
    }

    public void addStation(String name) {
        stationDAO.addStation(name);
    }

    public void addSchedule(String stationName, int trainNumber, Date time, int offset) {
        scheduleRecordDAO.addScheduleRecord(stationName, trainNumber, time, offset);
    }

    public String getPassengersByTrainInfo(int trainNum) {
        List<Passenger> passengerList = passengerDAO.getPassengersByTrain(trainNum);
        if(passengerList.isEmpty()){
            return "no data";
        }else{
            StringBuffer sb = new StringBuffer();
            for (Passenger passenger : passengerList) {
                sb.append(passenger.getName() + " " + passenger.getSurname() + " ;\n");
            }
            return sb.toString();
        }
    }


}
