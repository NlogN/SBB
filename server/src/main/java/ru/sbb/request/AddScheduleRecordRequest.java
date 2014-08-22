package ru.sbb.request;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class AddScheduleRecordRequest extends Request{
    private String psw;
    private String stationName;
    private int trainNumber;
    private Date time;
    private int offset;

    public AddScheduleRecordRequest(String stationName,int trainNumber, Date time, int offset, String psw){
        super(RequestType.ADD_SHEDULE_RECORD);
        this.psw = psw;
        this.stationName=stationName;
        this.trainNumber=trainNumber;
        this.time=time;
        this.offset=offset;
    }

    public String getPassword() {
        return psw;
    }

    public int getOffset() {
        return offset;
    }

    public Date getTime() {
        return time;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getStationName() {
        return stationName;
    }


}
