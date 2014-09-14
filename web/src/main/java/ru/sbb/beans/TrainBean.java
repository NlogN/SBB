package ru.sbb.beans;

import ru.sbb.entity.Train;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

//@ManagedBean(name = "trainBean")
//@SessionScoped
public class TrainBean implements Serializable {
    private String name;

    ManagerService managerService;

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationResult() {
        if (name == null) {
            return "";
        } else {
            return "Train with params: " + name;
        }
    }

    public String getTrains() {
        return "Train numbers: " + managerService.getTrainNumbers();
    }

    public List<Train> getAllTrains() {
        return managerService.getTrains();
    }

}