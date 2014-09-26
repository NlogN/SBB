package ru.sbb.dto;

/**
 * Created by Admin on 16.09.2014.
 */
public class StationScheduleRecord {
    private String trainNum;
    private java.util.Date time;

    public StationScheduleRecord(String trainNum, java.util.Date time){
        this.trainNum = trainNum;
        this.time = time;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public java.util.Date getTime() {
        return time;
    }

    public void setTime(java.util.Date time) {
        this.time = time;
    }




}
