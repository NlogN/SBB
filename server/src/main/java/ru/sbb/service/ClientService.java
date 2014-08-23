package ru.sbb.service;


import ru.sbb.DateBuilder;
import ru.sbb.dao.*;
import ru.sbb.entity.*;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */
public class ClientService {
    private static ClientService instance;
    private static PassengerDAO passengerDAO = new PassengerDAOImpl();
    private static TrainDAO trainDAO = new TrainDAOImpl();
    private static TicketDAO ticketDAO = new TicketDAOImpl();
    private static StationDAO stationDAO = new StationDAOImpl();
    private static ScheduleRecordDAO scheduleRecordDAO = new ScheduleRecordDAOImpl();

    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }


    public String getTrainsByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        List<Train> trainList = trainDAO.getTrainByRoute(lowerBound, upperBound, stationAName, stationBName);
             if(trainList.isEmpty()){
                 return "no such trains";
             } else{
                 StringBuffer sb = new StringBuffer();
                 for (Train train : trainList) {
                     sb.append(train.getNumber() + "; \n");
                 }
                 return sb.toString();
             }
    }


    public String getStationSchedule(String stationName) throws StationNotFoundException {
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
        if (scheduleList.isEmpty()) {
            return "no data";
        } else {
            StringBuffer sb = new StringBuffer();
            for (ScheduleRecord schedule : scheduleList) {
                sb.append("train "+schedule.getTrain().getNumber() + "    time: " + schedule.getTime() + "; \n");
            }
            return sb.toString();
        }
    }

    public void buyTicket(int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace) throws BuyTicketException, TrainNotFoundException {
        List<Train> list = trainDAO.getTrainByNum(trainNum);
        if (list.isEmpty()) {
            throw new TrainNotFoundException("Train not found!");
        } else {
            Train train = list.get(0);
            if (checkStationVisit(train, stationName, dateOfRace)) {
                if (!checkStartTime(train, stationName)) {
                    throw new BuyTicketException("registration on this train is closed");
                } else {
                    if (!checkSamePassengerNotReg(train, passenger.getName(), passenger.getSurname(), passenger.getDate())) {
                        throw new BuyTicketException("such passenger is already registered");
                    } else {
                        if (!checkNotFilledState(train, dateOfRace)) {
                            throw new BuyTicketException("no empty seats");
                        } else {
                            ticketDAO.addTicket(passenger, train, dateOfRace);
                        }

                    }
                }
            } else {
                throw new BuyTicketException("train not visit this station in this day");
            }

        }
    }

    boolean checkStationVisit(Train train, String stationName, java.util.Date dateOfRace) {
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        for (ScheduleRecord schedule : scheduleList) {
            if (schedule.getStation().getName().equals(stationName)) {

                if ((schedule.getUnixTime()>DateBuilder.getUnixTime(dateOfRace))&&(schedule.getUnixTime() < DateBuilder.getUnixTime(dateOfRace)+86400)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkStartTime(Train train, String stationName) {
        long currentTime = DateBuilder.getCurrentTime();
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        for (ScheduleRecord schedule : scheduleList) {
            if(schedule.getStation().getName().equals(stationName)){

                if ((schedule.getUnixTime() > (currentTime+360)) ) {
                    return true;
                }
            }

        }
        return false;
    }

    boolean checkNotFilledState(Train train, java.util.Date dateOfRace) {
        List<Ticket> list = ticketDAO.getTicketByDayAndTrain(train, dateOfRace);
        return train.getCapacity() > list.size();
    }

    boolean checkSamePassengerNotReg(Train train, String name, String surname, java.util.Date birthday) {
        List<Ticket> ticketList = train.getTicketList();
        for (Ticket ticket : ticketList) {
            Passenger passenger = ticket.getPassenger();
            if (passenger.getName().equals(name) && passenger.getSurname().equals(surname) && passenger.getDate().equals(birthday)) {
                return false;
            }
        }
        return true;
    }


}
