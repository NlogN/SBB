package ru.sbb.service;


import ru.sbb.DateBuilder;
import ru.sbb.dao.*;
import ru.sbb.entity.*;

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


    public String getStationSchedule(String stationName) {
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
        if (scheduleList.isEmpty()) {
            return "no data";
        } else {
            StringBuffer sb = new StringBuffer();
            for (ScheduleRecord schedule : scheduleList) {
                sb.append("train № "+schedule.getTrain().getNumber() + "    time: " + schedule.getTime() + "; \n");
            }
            return sb.toString();
        }
    }

    public boolean buyTicket(int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace) {
        List<Train> list = trainDAO.getTrainByNum(trainNum);
        if (list.isEmpty()) {
            System.out.println("Train not found!");
            return false;
        } else {
            Train train = list.get(0);
            if (checkStationVisit(train, stationName, dateOfRace)) {
                if (!checkStartTime(train, stationName)) {
                    System.out.println("registration on this train is closed");
                    return false;
                } else {
                    if (!checkSamePassengerNotReg(train, passenger.getName(), passenger.getSurname(), passenger.getDate())) {
                        System.out.println("such passenger is already registered");
                        return false;
                    } else {
                        if (!checkNotFilledState(train, dateOfRace)) {
                            System.out.println("no empty seats");
                            return false;
                        } else {
                            ticketDAO.addTicket(passenger, train, dateOfRace);
                        }

                    }
                }
            } else {
                System.out.println("train not visit this station in this day");
                return false;
            }

        }

        return true;
    }

    boolean checkStationVisit(Train train, String stationName, java.util.Date dateOfRace) {
        List<ScheduleRecord> scheduleList = train.getScheduleList();
        for (ScheduleRecord schedule : scheduleList) {
            if (schedule.getStation().getName().equals(stationName)) {
//                System.out.println(schedule.getUnixTime());
//                System.out.println(DateBuilder.getUnixTime(dateOfRace));
//                System.out.println(schedule.getUnixTime() - DateBuilder.getUnixTime(dateOfRace));
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
//                System.out.println(schedule.getUnixTime());
//                System.out.println(currentTime);
//                System.out.println(schedule.getUnixTime() - currentTime);
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
