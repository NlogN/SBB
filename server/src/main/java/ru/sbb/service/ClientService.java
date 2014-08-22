package ru.sbb.service;


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


    public List<Train> getTrainOnRouteABList(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName) {
        return trainDAO.getTrainOnRouteABList(lowerBound, upperBound, stationAName, stationBName);
    }


    public String getStationSchedule(String stationName) {
        List<ScheduleRecord> scheduleList = scheduleRecordDAO.getStationScheduleRecords(stationName);
        if(scheduleList.isEmpty()){
            return "no data";
        } else{
            StringBuffer sb = new StringBuffer();
            for (ScheduleRecord schedule : scheduleList) {
                sb.append(schedule.getTrain().getNumber() + " " + schedule.getTime() + "; \n");
            }
            return sb.toString();
        }
    }

    public boolean buyTicket(int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace) {
        return ticketDAO.buyTicket(trainNum, stationName, passenger, dateOfRace);
    }


}
