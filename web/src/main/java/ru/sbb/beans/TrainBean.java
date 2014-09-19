package ru.sbb.beans;

import ru.sbb.entity.Train;
import ru.sbb.service.ClientService;
import ru.sbb.service.ManagerService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;


public class TrainBean implements Serializable {
    private String number;
    private String capacity;
    private String operationResult = "";
    private ManagerService managerService;

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String num) {
        this.number = num;
    }

    public void setCapacity(String trainCapacity) {
        this.capacity = trainCapacity;
    }

    public String getCapacity() {
        return capacity;
    }


    public void addTrain(ActionEvent event) {
        if (number != null && capacity != null) {
            int num = Integer.parseInt(number);
            int cap = Integer.parseInt(capacity);
            managerService.addTrain(num, cap);
            setOperationResult("train added");
        }
    }


    public List<Train> getAllTrains() {
        return managerService.getTrains();
    }

}