package ru.sbb.service;


import ru.sbb.dto.StationScheduleRecord;
import ru.sbb.dao.PassengerDAO;
import ru.sbb.dao.ScheduleRecordDAO;
import ru.sbb.dao.TicketDAO;
import ru.sbb.dao.TrainDAO;
import ru.sbb.dto.TrainRecord;
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

import org.apache.log4j.Logger;


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

    private static Logger logger = Logger.getLogger(ClientService.class);

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


    public List<TrainRecord> getTrainsByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        logger.info("getTrainsByRoute request with param: " + "StationA: " + stationAName + ", StationB: " + stationBName);
        List<Train> trainList = trainDAO.getTrainByRoute(lowerBound, upperBound, stationAName, stationBName);
        List<TrainRecord> trains = new ArrayList<TrainRecord>();
        for (Train train : trainList) {
            TrainRecord newTrainRecord = new TrainRecord();
            newTrainRecord.setNumber(train.getNumber());
            newTrainRecord.setCapacity(train.getCapacity());
            trains.add(newTrainRecord);
        }
        return trains;
    }


    public List<StationScheduleRecord> getStationSchedule(String stationName) throws StationNotFoundException {
        logger.info("getStationSchedule request with param: " + "train: " + stationName);
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
        List<StationScheduleRecord> recordList = new ArrayList<StationScheduleRecord>();
        for (ScheduleRecord schedule : scheduleList) {
            StationScheduleRecord newStationScheduleRecord = new StationScheduleRecord();
            newStationScheduleRecord.setTrainNum(Integer.toString(schedule.getTrain().getNumber()));
            newStationScheduleRecord.setTime(schedule.getTime());
            recordList.add(newStationScheduleRecord);
        }
        return recordList;
    }


    public void buyTicket(int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace) throws BuyTicketException, TrainNotFoundException {
        logger.info("buyTicket request with param: " + "train: " + trainNum + ", " + "station: " + stationName);
        List<Train> trains = trainDAO.getTrainByNum(trainNum);
        if (trains.isEmpty()) {
            logger.error("Train not found: " + "train " + trainNum);
            throw new TrainNotFoundException("Train not found!", trainNum);
        } else {
            Train train = trains.get(0);
            if (!checkNotFilledState(train, dateOfRace)) {
                logger.error("no empty seats: " + "train " + trainNum);
                throw new BuyTicketException("no empty seats", trainNum, stationName, passenger, dateOfRace);
            } else {
                if (!checkStationVisit(train, stationName, dateOfRace)) {
                    logger.error("train not visit this station at this day: " + "train " + trainNum);
                    throw new BuyTicketException("train not visit this station at this day", trainNum, stationName, passenger, dateOfRace);
                } else {
                    if (!checkStartTime(train, stationName)) {
                        logger.error("registration on this train is closed: " + "train " + trainNum);
                        throw new BuyTicketException("registration on this train is closed", trainNum, stationName, passenger, dateOfRace);
                    } else {
                        if (!checkSamePassengerNotReg(train, passenger.getName(), passenger.getSurname(), passenger.getDate())) {
                            logger.error("such passenger is already registered: " + "train " + trainNum);
                            throw new BuyTicketException("such passenger is already registered", trainNum, stationName, passenger, dateOfRace);
                        } else {
                            ticketDAO.addTicket(passenger, train, dateOfRace);
                            logger.error("the operation was successful: " + "train " + trainNum);
                        }
                    }
                }
            }


        }
    }

    boolean checkStationVisit(Train train, String stationName, java.util.Date dateOfRace) {
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.findScheduleRecordsByStationNameAndTrain(train, stationName);
        if (!scheduleList.isEmpty()) {
            for (ScheduleRecord schedule : scheduleList) {
                if ((DateBuilder.getUnixTime(dateOfRace)) <= schedule.getUnixTime() && (schedule.getUnixTime() < DateBuilder.getUnixTime(dateOfRace) + 86400)) {
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
