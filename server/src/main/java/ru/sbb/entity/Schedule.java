package ru.sbb.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "station_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private java.util.Date time;

    @Column(name = "offset")
    private int offset;



    public Schedule() {

    }

    public int getId() {
        return scheduleId;
    }

    public void setId(int id) {
        this.scheduleId = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Date getTime() {
        return time;
    }

    public long getUnixTime() {
        return time.getTime()/1000;
    }

    public void setTime(Date t) {
        this.time = t;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    @Override
    public String toString() {
        return "ru.sbb.entity.Schedule{" +
                "id=" + scheduleId +
                ", trainId='" + train.getId() + '\'' +
                ", stationId='" + station.getId() +
                ", time='" + time+
        '}';
    }
}
