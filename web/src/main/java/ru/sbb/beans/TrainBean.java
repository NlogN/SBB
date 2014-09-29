package ru.sbb.beans;

import ru.sbb.dto.TrainRecord;
import ru.sbb.service.ManagerService;

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
            try {
                int num = Integer.parseInt(number);
                try {
                    int cap = Integer.parseInt(capacity);
                    managerService.addTrain(num, cap);
                    setOperationResult("train added");
                } catch (NumberFormatException e) {
                    setOperationResult("incorrect capacity num");
                }
            } catch (NumberFormatException e) {
                setOperationResult("incorrect train num");
            }
        }
    }


    public List<TrainRecord> getAllTrains() {
        return managerService.getTrains();
    }

}