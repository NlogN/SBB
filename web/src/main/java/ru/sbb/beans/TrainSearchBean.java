package ru.sbb.beans;


import ru.sbb.DateBuilder;
import ru.sbb.entity.Train;
import ru.sbb.service.ClientService;


import java.io.Serializable;
import java.text.ParseException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class TrainSearchBean implements Serializable {
    private String stationAName;
    private String stationBName;
    private String lowerBoundDay = "2014/12/12";
    private String upperBoundDay = "2014/05/12";
    private String lowerBoundTime = "01:01";
    private String upperBoundTime = "01:02";

    private ClientService clientService;

    public String getUpperBoundTime() {
        return upperBoundTime;
    }

    public void setUpperBoundTime(String upperBoundTime) {
        this.upperBoundTime = upperBoundTime;
    }

    public String getLowerBoundTime() {
        return lowerBoundTime;
    }

    public void setLowerBoundTime(String lowerBoundTime) {
        this.lowerBoundTime = lowerBoundTime;
    }

    public String getUpperBoundDay() {
        return upperBoundDay;
    }

    public void setUpperBoundDay(String upperBoundDay) {
        this.upperBoundDay = upperBoundDay;
    }

    public String getLowerBoundDay() {
        return lowerBoundDay;
    }

    public void setLowerBoundDay(String lowerBoundDay) {
        this.lowerBoundDay = lowerBoundDay;
    }

    public String getStationBName() {
        return stationBName;
    }

    public void setStationBName(String stationBName) {
        this.stationBName = stationBName;
    }

    public String getStationAName() {
        return stationAName;
    }

    public void setStationAName(String stationAName) {
        this.stationAName = stationAName;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public List<Train> getTrainsByResponse() {
        if (stationAName == null || stationBName == null) {
            return new ArrayList<Train>();
        }
        try {
            java.util.Date lowerBound = DateBuilder.createDateTime(lowerBoundDay + " " + lowerBoundTime + ":00");
            java.util.Date upperBound = DateBuilder.createDateTime(upperBoundDay + " " + upperBoundTime + ":00");
            return clientService.getTrainsByRoute(lowerBound, upperBound, stationAName, stationBName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<Train>();
    }


}