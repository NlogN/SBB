import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    private int ticket_id;

    @Column(name = "passenger_id")
    private int passenger_id;

    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;


    public Ticket() {

    }

    public int getId() {
        return ticket_id;
    }

    public void setId(int id) {
        this.ticket_id = id;
    }

    public int getPassengerId() {
        return passenger_id;
    }

    public void setPassengerId(int p) {
        this.passenger_id = p;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train t) {
        this.train = t;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + ticket_id +
                ", tarin_id='" + train.getId() + '\'' +
                ", tarin_number='" + train.getNumber() + '\'' +
                ", passanger_id='" + passenger_id +
                ", date='" + date +
                '}';
    }
}
