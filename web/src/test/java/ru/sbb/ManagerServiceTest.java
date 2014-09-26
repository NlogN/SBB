package ru.sbb;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.sbb.exception.StationNotFoundException;
import ru.sbb.service.ManagerService;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 02.09.14
 */
public class ManagerServiceTest {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("beans.xml");

    ManagerService managerService = (ManagerService) context.getBean("managerService");


//    @Test
//    public void testGetTrainNumbers() throws IOException, StationNotFoundException {
//        String res = managerService.getTrainNumbers();
//        System.out.println(res);
//    }


//    @Test
//    public void testGetPassengersInfo1() throws IOException, StationNotFoundException {
//        String res = managerService.getPassengersInfoByTrainNum(123);
//        System.out.println(res);
//    }

//    @Test
//    public void testGetPassengersInfo2() throws IOException, StationNotFoundException {
//        String result = managerService.getPassengersInfoByTrainNum(555);
//        assertTrue(result.equals("no data"));
//    }


}