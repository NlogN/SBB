package ru.sbb;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.entity.Passenger;
import ru.sbb.entity.Train;
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
    ApplicationContext context =
            new ClassPathXmlApplicationContext("beans.xml");

    ClientService clientService = (ClientService) context.getBean("clientService");


    @Test(expected = StationNotFoundException.class)
    public void testGetStationSchedule1() throws IOException, StationNotFoundException {
        List<StationScheduleRecord> recordList = clientService.getStationSchedule("Moskow123");
        for (StationScheduleRecord record:recordList){
            System.out.println(record.getTrainNum()+" "+record.getTime());
        }

    }

    @Test
    public void testGetStationSchedule2() throws IOException, StationNotFoundException {
        List<StationScheduleRecord> recordList = clientService.getStationSchedule("Moskow");
        for (StationScheduleRecord record:recordList){
            System.out.println(record.getTrainNum()+" "+record.getTime());
        }
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
        List<Train> trainList = clientService.getTrainsByRoute(lowerBound, upperBound, "Moskow", "Omsk");
        for (Train train:trainList){
            System.out.println(train.getNumber());
        }

    }


}