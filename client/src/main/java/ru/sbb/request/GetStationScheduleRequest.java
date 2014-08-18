package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 19.08.14
 */
public class GetStationScheduleRequest extends Request implements Serializable {
    private final String stationName;

    public GetStationScheduleRequest(String stationName){
        this.stationName=stationName;
        type=RequestType.GET_STATION_SCHEDULE;
    }

    String getStationName(){
        return stationName;
    }

    public String toString() {
        return "[" + stationName + "] ";
    }
}
