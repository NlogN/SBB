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
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "order")
    private int order;



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

    public void setTime(Date t) {
        this.time = t;
    }

    public int getOrder() {
        return order;
    }

    public void setOrderId(int order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + scheduleId +
                ", trainId='" + train.getId() + '\'' +
                ", stationId='" + station.getId() +
                ", time='" + time+
        '}';
    }
}
