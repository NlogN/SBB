

public class StationScheduleBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStationSchedule() {
        return "Schedule of station " + name;
    }


}