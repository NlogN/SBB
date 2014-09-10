package ru.sbb;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.dao.PassengerDAOImpl;
import ru.sbb.entity.Passenger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */

public class PassengerDaoSimpleTest {
    public static void main(String[] args) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        PassengerDAOImpl obj = new PassengerDAOImpl();
//        obj.setEntityManager(entityManager);
//
//        List<Passenger> passengerList = obj.getPassengersByTrain(123);
//        for (Passenger passenger:passengerList){
//            System.out.println(passenger.toString());
//        }
    }

}