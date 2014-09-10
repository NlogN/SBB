package ru.sbb;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.entity.Passenger;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.service.ClientService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class ClientServiceTest2 {


    public static void main(String[] args) throws StationNotFoundException, ParseException {
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("beans.xml");
//
//        ClientService clientService = (ClientService) context.getBean("clientService");
//        String result = clientService.getStationSchedule("Moskow");
//        System.out.println(result);
//
//        Date lowerBound = DateBuilder.createDate("1991/01/01 01:02:12");
//        Date upperBound = DateBuilder.createDate("2016/01/01 01:02:12");
//        String result1 = clientService.getTrainsByRoute(lowerBound, upperBound, "Moskow", "Omsk");
//        System.out.println(result1);

    }


}