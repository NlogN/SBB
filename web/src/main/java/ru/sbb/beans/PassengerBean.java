package ru.sbb.beans;

import ru.sbb.dto.PassengerRecord;
import ru.sbb.service.ManagerService;

import java.io.Serializable;
import java.util.List;


public class PassengerBean implements Serializable {
    private String number;
    private String operationResult = "";
    private List<PassengerRecord> passengers;

    private ManagerService managerService;

    public List<PassengerRecord> getPassengers() {
        return passengers;
    }

    public void setPassengers() {
        int trainNum = Integer.parseInt(number);
        this.passengers = managerService.getPassengersInfoByTrainNum(trainNum);
    }

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



    public String  getPassengersPage() {
            setPassengers();
            if(passengers.isEmpty()){
                setOperationResult("no data");
                return "viewPassengers";
            }else{
                return "viewPassengersResp";
            }
    }


}