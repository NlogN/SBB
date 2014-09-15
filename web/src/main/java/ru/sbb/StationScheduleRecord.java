package ru.sbb;

/**
 * Created by Admin on 16.09.2014.
 */
public class StationScheduleRecord {
    //private String name;
    private String trainNum;
    private java.util.Date time;

    public StationScheduleRecord(String trainNum, java.util.Date time){
      //  this.name = name;
        this.trainNum = trainNum;
        this.time = time;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public java.util.Date getTime() {
        return time;
    }

    public void setTime(java.util.Date time) {
        this.time = time;
    }




}
