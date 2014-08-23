package ru.sbb.request;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class BuyTicketRequest extends Request {
    private String name;
    private String surname;
    private int trainNumber;
    private Date dayOfBirth;
    private String stationName;
    private Date dateOfRace;

    public BuyTicketRequest(String name, String surname, Date dayOfBirth, int trainNumber, String stationName, Date dateOfRace) {
        super(RequestType.BUY_TICKET);
        this.name = name;
        this.surname = surname;
        this.dayOfBirth = dayOfBirth;
        this.trainNumber = trainNumber;
        this.stationName = stationName;
        this.dateOfRace = dateOfRace;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public String getStationName() {
        return stationName;
    }

    public Date getDateOfRace() {
        return dateOfRace;
    }

}
