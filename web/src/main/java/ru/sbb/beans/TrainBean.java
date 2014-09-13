package ru.sbb.beans;

import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

//@ManagedBean(name = "trainBean")
//@SessionScoped
public class TrainBean implements Serializable {
    private String name;

    ManagerService managerService;

    public void setManagerService(ManagerService managerService){
        this.managerService=managerService;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationResult() {
        return "Train with params: " + name;
    }

    public String getTrains() {
        return "Train numbers: " + managerService.getTrainNumbers();
    }

}