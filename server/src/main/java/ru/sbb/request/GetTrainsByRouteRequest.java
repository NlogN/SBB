package ru.sbb.request;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */

public class GetTrainsByRouteRequest extends Request {
    private String stationAName;
    private String stationBName;
    private Date lowerBound;
    private Date upperBound;

    public GetTrainsByRouteRequest(String stationAName, String stationBName, Date lowerBound, Date upperBound) {
        super(RequestType.TRAIN_BY_ROUTE);
        this.stationAName = stationAName;
        this.stationBName = stationBName;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }


    public Date getUpperBound() {
        return upperBound;
    }

    public Date getLowerBound() {
        return lowerBound;
    }

    public String getStationBName() {
        return stationBName;
    }

    public String getStationAName() {
        return stationAName;
    }


}
