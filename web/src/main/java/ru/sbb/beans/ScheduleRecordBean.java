package ru.sbb.beans;


import ru.sbb.DateBuilder;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.exception.TrainNotFoundException;
import ru.sbb.service.ManagerService;

import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.text.ParseException;


public class ScheduleRecordBean implements Serializable {
    private String stationName;
    private String trainNumber;
    private String date = "2014/12/12";
    private String time = "01:01";
    private String offset = "100";
    private String operationResult = "";

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    private ManagerService managerService;

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void addScheduleRecord(ActionEvent event) {
        try {
            java.util.Date scheduleRecordTime = DateBuilder.createDateTime(date + " " + time + ":00");
            int trainNum = Integer.parseInt(trainNumber);
            int stationOffset = Integer.parseInt(trainNumber);
            managerService.addScheduleRecord(stationName,trainNum,scheduleRecordTime,stationOffset);
            setOperationResult("the operation was successful");
        } catch (ParseException e) {
            setOperationResult("incorrect date format");
        } catch (StationNotFoundException e) {
            setOperationResult(e.getMessage());
        } catch (TrainNotFoundException e) {
            setOperationResult(e.getMessage());
        }catch (NumberFormatException e) {
            setOperationResult("incorrect train number format");
        }

    }


}