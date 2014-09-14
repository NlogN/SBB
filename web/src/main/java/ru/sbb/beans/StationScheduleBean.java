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


public class StationScheduleBean implements Serializable {

    private String name;

    ClientService clientService;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void submit() {

    }

    public String getStationSchedule() {
        if (name == null) {
            return "";
        } else {
            try {
                return clientService.getStationSchedule(name);
            } catch (StationNotFoundException e) {
                return "Station " + name + " Not Found";
            }
        }


    }


}