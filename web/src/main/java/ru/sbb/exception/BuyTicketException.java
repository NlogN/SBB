package ru.sbb.exception;

import ru.sbb.entity.Passenger;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 24.08.14
 */
public class BuyTicketException extends Exception {
    private int trainNum;
    private String stationName;
    private Passenger passenger;
    private java.util.Date dateOfRace;

    public BuyTicketException(String text) {
        super(text);
    }

    public BuyTicketException(String text, int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace) {
        super(text);
        this.trainNum = trainNum;
        this.stationName = stationName;
        this.passenger = passenger;
        this.dateOfRace = dateOfRace;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public int getTrainNum() {
        return trainNum;
    }

    public String getStationName() {
        return stationName;
    }

    public Date getDateOfRace() {
        return dateOfRace;
    }

}
