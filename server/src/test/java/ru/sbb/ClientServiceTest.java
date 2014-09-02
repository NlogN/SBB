package ru.sbb;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.service.ClientService;

import static junit.framework.TestCase.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class ClientServiceTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    TrainDAO trainDAO = new TrainDAOImpl(entityManager);
    TicketDAO ticketDAO = new TicketDAOImpl(entityManager);
    ScheduleRecordDAO scheduleRecordDAO = new ScheduleRecordDAOImpl(entityManager);
    PassengerDAO passengerDAO = new PassengerDAOImpl(entityManager);
    ClientService clientService = new ClientService(ticketDAO, trainDAO, scheduleRecordDAO, passengerDAO);


    @Test(expected = StationNotFoundException.class)
    public void testGetStationSchedule1() throws IOException, StationNotFoundException {
        String result = clientService.getStationSchedule("Moskow123");
        System.out.println(result);
    }

    @Test
    public void testGetStationSchedule2() throws IOException, StationNotFoundException {
        String result = clientService.getStationSchedule("Moskow");
        System.out.println(result);
    }

    @Test(expected = BuyTicketException.class)
    public void testBuyTicket1() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        Passenger passenger = new Passenger();
        Date birthDate = DateBuilder.createDate("1991/01/01");
        passenger.setDate(birthDate);
        passenger.setName("Peter");
        passenger.setSurname("Ivanov");
        Date dateOfRace = DateBuilder.createDate("2014/08/22");
        clientService.buyTicket(123, "Novosibirsk312", passenger, dateOfRace);
    }

    @Test(expected = BuyTicketException.class)
    public void testBuyTicket2() throws IOException, StationNotFoundException, ParseException, BuyTicketException, TrainNotFoundException {
        Passenger passenger = new Passenger();
        Date birthDate = DateBuilder.createDate("1991/01/01");
        passenger.setDate(birthDate);
        passenger.setName("Peter");
        passenger.setSurname("Ivanov");
        Date dateOfRace = DateBuilder.createDate("2011/08/22");
        clientService.buyTicket(123, "Novosibirsk", passenger, dateOfRace);
    }

    @Test
    public void testGetTrainsByRoute() throws ParseException {
        Date lowerBound = DateBuilder.createDate("1991/01/01 01:02:12");
        Date upperBound = DateBuilder.createDate("2016/01/01 01:02:12");
        String result = clientService.getTrainsByRoute(lowerBound, upperBound, "Moskow", "Omsk");
        String expectedResult = 300 + "; \n" + 321 + "; \n";
        assertTrue(result.equals(expectedResult));
    }


}