package ru.sbb.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbb.dao.ScheduleRecordDAO;
import ru.sbb.dao.ScheduleRecordDAOImpl;
import ru.sbb.entity.ScheduleRecord;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.service.ClientService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

//import javax.inject.Inject;
//import javax.inject.Named;
import org.springframework.context.annotation.Scope;

//@Named
//@Scope("session")
public class StationScheduleBean implements Serializable {

    private String name;

   // @Inject
    ScheduleRecordDAO scheduleRecordDAOImpl;

    public void setScheduleRecordDAOImpl(ScheduleRecordDAO scheduleRecordDAOImpl){
        this.scheduleRecordDAOImpl=scheduleRecordDAOImpl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStationSchedule() {
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("beans.xml");
//
//        ClientService clientService = (ClientService) context.getBean("clientService");

//
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sbb_unit");
//
////
//        ScheduleRecordDAOImpl scheduleRecordDAO = new ScheduleRecordDAOImpl();
//        scheduleRecordDAO.setEntityManager(entityManagerFactory);
//
//        if (name==null){
//            name="";
//        }
        try {
           // String result = clientService.getStationSchedule(name);
            String result = "";
            List<ScheduleRecord> recordList = scheduleRecordDAOImpl.getStationScheduleRecords(name);
            for (ScheduleRecord record:recordList){
                result+=record.toString()+" ";
            }
            return result;
        } catch (StationNotFoundException e) {
            return "Station Not Found";
        }
      // return "Schedule of station " + name;
    }


}