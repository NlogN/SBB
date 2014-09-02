package ru.sbb;


import org.junit.Test;
import ru.sbb.dao.*;

import ru.sbb.exception.StationNotFoundException;
import ru.sbb.service.ManagerService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class ManagerServiceTest {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    TrainDAO trainDAO = new TrainDAOImpl(entityManager);
    ScheduleRecordDAO scheduleRecordDAO = new ScheduleRecordDAOImpl(entityManager);
    PassengerDAO passengerDAO = new PassengerDAOImpl(entityManager);
    StationDAO stationDAO = new StationDAOImpl(entityManager);
    ManagerService managerService = new ManagerService(passengerDAO, trainDAO, stationDAO, scheduleRecordDAO);


    @Test
    public void testGetTrainNumbers() throws IOException, StationNotFoundException {
        String res = managerService.getTrainNumbers();
        System.out.println(res);
    }


    @Test
    public void testGetPassengersInfo1() throws IOException, StationNotFoundException {
        String res = managerService.getPassengersInfoByTrainNum(123);
        System.out.println(res);
    }

    @Test
    public void testGetPassengersInfo2() throws IOException, StationNotFoundException {
        String result = managerService.getPassengersInfoByTrainNum(555);
        assertTrue(result.equals("no data"));
    }


}