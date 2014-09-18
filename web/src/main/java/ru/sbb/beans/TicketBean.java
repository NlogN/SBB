package ru.sbb.beans;


import ru.sbb.DateBuilder;
import ru.sbb.entity.Passenger;
import ru.sbb.exception.BuyTicketException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.service.ClientService;

import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.text.ParseException;

public class TicketBean implements Serializable {
    private String name;
    private String surname;
    private String trainNumber;
    private String station;
    private String bithday = "1990/01/01";
    private String dateOfRace = "2014/01/01";
    private String operationResult = "";

    private ClientService clientService;

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getBithday() {
        return bithday;
    }

    public void setBithday(String bithday) {
        this.bithday = bithday;
    }

    public String getDateOfRace() {
        return dateOfRace;
    }

    public void setDateOfRace(String dateOfRace) {
        this.dateOfRace = dateOfRace;
    }


    public void buyTicket(ActionEvent event) {
        Passenger passenger = new Passenger();
        passenger.setName(name);
        passenger.setSurname(surname);
        try {
            java.util.Date dayOfBirth = DateBuilder.createDate(bithday);
            passenger.setDate(dayOfBirth);
        } catch (ParseException e) {
            setOperationResult("dayOfBirth: incorrect date format");
            return;
        }
        try {
            java.util.Date dayOfRace = DateBuilder.createDate(dateOfRace);
            clientService.buyTicket(Integer.parseInt(trainNumber), station, passenger, dayOfRace);
            setOperationResult("the operation was successful");
        } catch (ParseException e) {
            setOperationResult("dateOfRace: incorrect date format");
        } catch (TrainNotFoundException e) {
            setOperationResult(e.getMessage());
        } catch (BuyTicketException e) {
            setOperationResult(e.getMessage());
        }

    }


}