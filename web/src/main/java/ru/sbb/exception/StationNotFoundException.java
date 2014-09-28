package ru.sbb.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 24.08.14
 */
public class StationNotFoundException extends Exception {
    private String stationName;

    public StationNotFoundException(String text) {
        super(text);
    }

    public StationNotFoundException(String text, String stationName) {
        super(text);
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
