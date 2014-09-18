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
    private String number;
    private String capacity;

    private ManagerService managerService;

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String num) {
        this.number = num;
    }

    public void setCapacity(String trainCapacity) {
//        int capacity = Integer.parseInt(trainCapacity);
        this.capacity = trainCapacity;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getOperationResult() {
        return "Train with params: " + number;
    }

    public String addTrain() {
        if (number == null || capacity == null) {
            return "";
        } else {
            int num = Integer.parseInt(number);
            int cap = Integer.parseInt(capacity);
            managerService.addTrain(num, cap);
            return "train added";
        }
    }

    public String getTrains() {
        return "Train numbers: " + managerService.getTrainNumbers();
    }

    public List<Train> getAllTrains() {
        return managerService.getTrains();
    }

}