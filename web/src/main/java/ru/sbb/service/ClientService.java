package ru.sbb.service;


import ru.sbb.StationScheduleRecord;
import ru.sbb.dao.PassengerDAO;
import ru.sbb.dao.ScheduleRecordDAO;
import ru.sbb.dao.TicketDAO;
import ru.sbb.dao.TrainDAO;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.ScheduleRecord;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.DateBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */
public class ClientService {
    private TicketDAO ticketDAO;
    private ScheduleRecordDAO scheduleRecordDAO;
    private TrainDAO trainDAO;
    private PassengerDAO passengerDAO;

    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
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



//    public String getTrainsByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
//        List<Train> trainList = trainDAO.getTrainByRoute(lowerBound, upperBound, stationAName, stationBName);
//        if (trainList.isEmpty()) {
//            return "no such trains";
//        } else {
//            StringBuffer sb = new StringBuffer();
//            for (Train train : trainList) {
//                sb.append(train.getNumber() + "; \n");
//            }
//            return sb.toString();
//        }
//    }

    public List<Train> getTrainsByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        List<Train> trainList = trainDAO.getTrainByRoute(lowerBound, upperBound, stationAName, stationBName);
        return trainList;
//        if (trainList.isEmpty()) {
//            return "no such trains";
//        } else {
//            StringBuffer sb = new StringBuffer();
//            for (Train train : trainList) {
//                sb.append(train.getNumber() + "; \n");
//            }
//            return sb.toString();
//        }
    }


    public List<StationScheduleRecord> getStationSchedule(String stationName) throws StationNotFoundException {
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
        List<StationScheduleRecord> recordList = new ArrayList<StationScheduleRecord>();
        if (scheduleList.isEmpty()) {
            return recordList;
        } else {
//            StringBuffer sb = new StringBuffer();
//            for (ScheduleRecord schedule : scheduleList) {
//                sb.append("train " + schedule.getTrain().getNumber() + "    time: " + schedule.getTime() + "; \n");
//            }

            for (ScheduleRecord schedule : scheduleList) {
                recordList.add(new StationScheduleRecord(Integer.toString(schedule.getTrain().getNumber()),schedule.getTime()));
            }
            return recordList;
        }
    }

//    public String getStationSchedule(String stationName) throws StationNotFoundException {
//        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
//        if (scheduleList.isEmpty()) {
//            return "no data";
//        } else {
//            StringBuffer sb = new StringBuffer();
//            for (ScheduleRecord schedule : scheduleList) {
//                sb.append("train " + schedule.getTrain().getNumber() + "    time: " + schedule.getTime() + "; \n");
//            }
//            return sb.toString();
//        }
//    }

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
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.findScheduleRecordsByStationNameAndTrain(train, stationName);
        if (!scheduleList.isEmpty()) {
            for (ScheduleRecord schedule : scheduleList) {
                if ((schedule.getUnixTime() > DateBuilder.getUnixTime(dateOfRace)) && (schedule.getUnixTime() < DateBuilder.getUnixTime(dateOfRace) + 86400)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkStartTime(Train train, String stationName) {
        long currentTime = DateBuilder.getCurrentTime();
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.findScheduleRecordsByStationNameAndTrain(train, stationName);
        if (!scheduleList.isEmpty()) {
            for (ScheduleRecord schedule : scheduleList) {
                if ((schedule.getUnixTime() > (currentTime + 360))) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkNotFilledState(Train train, java.util.Date dateOfRace) {
        List<Ticket> list = ticketDAO.getTicketsByDayAndTrain(train, dateOfRace);
        return train.getCapacity() > list.size();
    }

    boolean checkSamePassengerNotReg(Train train, String name, String surname, java.util.Date birthday) {
        List<Passenger> passengerList = passengerDAO.getPassengersByInfo(train, name, surname, birthday);
        return passengerList.isEmpty();
    }


}
