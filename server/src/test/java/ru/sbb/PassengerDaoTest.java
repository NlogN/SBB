package ru.sbb;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.service.ClientService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class PassengerDaoTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        PassengerDAOImpl obj = (PassengerDAOImpl) context.getBean("passengerDAO");

        List<Passenger> passengerList = obj.getPassengersByTrain(123);
        for (Passenger passenger:passengerList){
            System.out.println(passenger.toString());
        }
    }

}