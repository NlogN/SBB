package ru.sbb.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @Column(name = "station_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int stationId;

    @Column(name = "name")
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "station")
    private List<ScheduleRecord> scheduleList;


    public Station() {

    }

    public int getId() {
        return stationId;
    }

    public void setId(int id) {
        this.stationId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduleRecord> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleRecord> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void printSchedule() {
        StringBuffer sb = new StringBuffer();
        for (ScheduleRecord schedule : scheduleList) {
            sb.append(schedule.getTrain().getNumber() + " " + schedule.getTime() + "\n");
        }
        System.out.println(sb.toString());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (ScheduleRecord schedule : scheduleList) {
            sb.append(schedule.getTrain().getId() + " ");
        }
        return "ru.sbb.entity.Station{" +
                "id=" + stationId +
                ", name='" + name +
                ", trains='" + sb.toString() +
                '}';
    }
}
