package ru.sbb.beans;

import ru.sbb.entity.Train;
import ru.sbb.service.ManagerService;

import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;


public class StationBean implements Serializable {
    private String name;
    private String operationResult = "";

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ManagerService managerService;

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }



    public void addStation(ActionEvent event) {
        managerService.addStation(name);
        this.setOperationResult("station added");
    }



}