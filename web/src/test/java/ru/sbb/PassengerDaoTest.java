package ru.sbb;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.dao.*;
import ru.sbb.entity.Passenger;

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