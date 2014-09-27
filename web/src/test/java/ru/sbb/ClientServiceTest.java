package ru.sbb;



import org.junit.Test;

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
import ru.sbb.service.ClientService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class ClientServiceTest {


    @Test
    public void testBuyTicketTrainNotFound() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(new ArrayList<Train>());

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);

        try {
            clientService.buyTicket(123, "Moskow", new Passenger(), new Date());
            fail("Should have thrown an TrainNotFoundException!");
        } catch (TrainNotFoundException e) {
            assertTrue(e.getMessage().contains("Train not found"));
        }
    }


    @Test
    public void testBuyTicketSuccessOperation() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        List<Train> trainList = new ArrayList<Train>();
        Train train = new Train();
        train.setNumber(123);
        train.setCapacity(100);
        trainList.add(train);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(trainList);

        TicketDAO ticketDAOMock = mock(TicketDAO.class);
        Date dateOfRace = DateBuilder.createDate("2025/01/01");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket());
        when(ticketDAOMock.getTicketsByDayAndTrain(train, dateOfRace)).thenReturn(ticketList);

        ScheduleRecordDAO scheduleRecordDAOMock = mock(ScheduleRecordDAO.class);
        List<ScheduleRecord> scheduleRecordList = new ArrayList<ScheduleRecord>();
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setTime(dateOfRace);
        scheduleRecordList.add(scheduleRecord);
        when(scheduleRecordDAOMock.findScheduleRecordsByStationNameAndTrain(train, "Moskow")).thenReturn(scheduleRecordList);

        PassengerDAO passengerDAOMock = mock(PassengerDAO.class);
        Passenger passenger = new Passenger();
        passenger.setName("Petr");
        passenger.setSurname("Ivanov");
        Date dateOfBirth = DateBuilder.createDate("1985/03/16");
        passenger.setDate(dateOfBirth);
        List<Passenger> passengerList = new ArrayList<Passenger>();
        passengerList.add(passenger);
        when(passengerDAOMock.getPassengersByInfo(train, passenger.getName(), passenger.getSurname(), passenger.getDate())).thenReturn(passengerList);

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);
        clientService.setScheduleRecordDAO(scheduleRecordDAOMock);
        clientService.setTicketDAO(ticketDAOMock);
        clientService.setPassengerDAO(passengerDAOMock);

        Passenger testPassenger = new Passenger();
        testPassenger.setName("Ivan");
        testPassenger.setSurname("Petrov");
        testPassenger.setDate(passenger.getDate());
        clientService.buyTicket(123, "Moskow", testPassenger, dateOfRace);
    }

    @Test
    public void testBuyTicketSamePassengerAlreadyRegistered() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        List<Train> trainList = new ArrayList<Train>();
        Train train = new Train();
        train.setNumber(123);
        train.setCapacity(100);
        trainList.add(train);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(trainList);

        TicketDAO ticketDAOMock = mock(TicketDAO.class);
        Date dateOfRace = DateBuilder.createDate("2025/01/01");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket());
        when(ticketDAOMock.getTicketsByDayAndTrain(train, dateOfRace)).thenReturn(ticketList);

        ScheduleRecordDAO scheduleRecordDAOMock = mock(ScheduleRecordDAO.class);
        List<ScheduleRecord> scheduleRecordList = new ArrayList<ScheduleRecord>();
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setTime(dateOfRace);
        scheduleRecordList.add(scheduleRecord);
        when(scheduleRecordDAOMock.findScheduleRecordsByStationNameAndTrain(train, "Moskow")).thenReturn(scheduleRecordList);

        PassengerDAO passengerDAOMock = mock(PassengerDAO.class);
        Passenger passenger = new Passenger();
        passenger.setName("Petr");
        passenger.setSurname("Ivanov");
        Date dateOfBirth = DateBuilder.createDate("1985/03/16");
        passenger.setDate(dateOfBirth);
        List<Passenger> passengerList = new ArrayList<Passenger>();
        passengerList.add(passenger);
        when(passengerDAOMock.getPassengersByInfo(train, passenger.getName(), passenger.getSurname(), passenger.getDate())).thenReturn(passengerList);

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);
        clientService.setScheduleRecordDAO(scheduleRecordDAOMock);
        clientService.setTicketDAO(ticketDAOMock);
        clientService.setPassengerDAO(passengerDAOMock);

        try {
            Passenger testPassenger = new Passenger();
            testPassenger.setName(passenger.getName());
            testPassenger.setSurname(passenger.getSurname());
            testPassenger.setDate(passenger.getDate());
            clientService.buyTicket(123, "Moskow", testPassenger, dateOfRace);
            fail("Should have thrown an BuyTicketException!");
        } catch (BuyTicketException e) {
            assertTrue(e.getMessage().contains("such passenger is already registered"));
        }
    }


    @Test
    public void testBuyTicketRegistrationClosed() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        List<Train> trainList = new ArrayList<Train>();
        Train train = new Train();
        train.setNumber(123);
        train.setCapacity(100);
        trainList.add(train);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(trainList);

        TicketDAO ticketDAOMock = mock(TicketDAO.class);
        Date dateOfRace = DateBuilder.createDate("1900/01/01");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket());
        when(ticketDAOMock.getTicketsByDayAndTrain(train, dateOfRace)).thenReturn(ticketList);

        ScheduleRecordDAO scheduleRecordDAOMock = mock(ScheduleRecordDAO.class);
        List<ScheduleRecord> scheduleRecordList = new ArrayList<ScheduleRecord>();
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setTime(dateOfRace);
        scheduleRecordList.add(scheduleRecord);
        when(scheduleRecordDAOMock.findScheduleRecordsByStationNameAndTrain(train, "Moskow")).thenReturn(scheduleRecordList);

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);
        clientService.setScheduleRecordDAO(scheduleRecordDAOMock);
        clientService.setTicketDAO(ticketDAOMock);

        try {
            clientService.buyTicket(123, "Moskow", new Passenger(), dateOfRace);
            fail("Should have thrown an BuyTicketException!");
        } catch (BuyTicketException e) {
            assertTrue(e.getMessage().contains("registration on this train is closed"));
        }
    }

    @Test
    public void testBuyTicketTrainNotVisitStation() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        List<Train> trainList = new ArrayList<Train>();
        Train train = new Train();
        train.setNumber(123);
        train.setCapacity(100);
        trainList.add(train);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(trainList);

        TicketDAO ticketDAOMock = mock(TicketDAO.class);
        Date date = DateBuilder.createDate("1900/01/01");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket());
        when(ticketDAOMock.getTicketsByDayAndTrain(train, date)).thenReturn(ticketList);

        ScheduleRecordDAO scheduleRecordDAOMock = mock(ScheduleRecordDAO.class);
        when(scheduleRecordDAOMock.findScheduleRecordsByStationNameAndTrain(train, "Moskow")).thenReturn(new ArrayList<ScheduleRecord>());

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);
        clientService.setScheduleRecordDAO(scheduleRecordDAOMock);
        clientService.setTicketDAO(ticketDAOMock);

        try {
            clientService.buyTicket(123, "Moskow", new Passenger(), date);
            fail("Should have thrown an BuyTicketException!");
        } catch (BuyTicketException e) {
            assertTrue(e.getMessage().contains("train not visit this station"));
        }

    }



    @Test
    public void testBuyTicketNoEmptySeats() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        TrainDAO trainDAOMock = mock(TrainDAO.class);
        List<Train> trainList = new ArrayList<Train>();
        Train train = new Train();
        train.setNumber(123);
        train.setCapacity(0);
        trainList.add(train);
        when(trainDAOMock.getTrainByNum(123)).thenReturn(trainList);

        TicketDAO ticketDAOMock = mock(TicketDAO.class);
        Date date = DateBuilder.createDate("1900/01/01");
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(new Ticket());
        when(ticketDAOMock.getTicketsByDayAndTrain(train, date)).thenReturn(ticketList);

        ClientService clientService = new ClientService();
        clientService.setTrainDAO(trainDAOMock);
        clientService.setTicketDAO(ticketDAOMock);

        try {
            clientService.buyTicket(123, "Moskow", new Passenger(), date);
            fail("Should have thrown an BuyTicketException!");
        } catch (BuyTicketException e) {
            assertTrue(e.getMessage().contains("no empty seats"));
        }

    }


}