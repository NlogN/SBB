package ru.sbb.beans;


import ru.sbb.StationScheduleRecord;
import ru.sbb.exception.StationNotFoundException;
import ru.sbb.service.ClientService;
import java.io.Serializable;
import java.util.List;


public class StationScheduleBean implements Serializable {
    private String name;
    private String operationResult = "";
    private ClientService clientService;
    private List<StationScheduleRecord> stationSchedule;

    public List<StationScheduleRecord> getStationSchedule() {
        return stationSchedule;
    }

    public void setStationSchedule() throws StationNotFoundException {
        this.stationSchedule = clientService.getStationSchedule(name);
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String  getStationSchedulePage() {
        try {
            setStationSchedule();
            return "stationScheduleResp";
        } catch (StationNotFoundException e) {
            setOperationResult(e.getMessage());
            return "stationSchedule";
        }
    }


}