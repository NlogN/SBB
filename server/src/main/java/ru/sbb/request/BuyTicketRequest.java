package ru.sbb.request;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class BuyTicketRequest extends Request {
    private String name;
    private String surname;
    private int trainNumber;
    private String dayOfBirth;
    private String stationName;
    private String dateOfRace;

    public BuyTicketRequest(String name, String surname, String dayOfBirth, int trainNumber, String stationName, String dateOfRace) {
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

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public String getStationName() {
        return stationName;
    }

    public String getDateOfRace() {
        return dateOfRace;
    }

}
