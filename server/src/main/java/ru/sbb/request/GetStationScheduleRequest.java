package ru.sbb.request;



/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetStationScheduleRequest extends Request {
    private final String stationName;

    public GetStationScheduleRequest(String stationName) {
        super(RequestType.GET_STATION_SCHEDULE);
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }


    public String toString() {
        return "[" + stationName + "] ";
    }
}
