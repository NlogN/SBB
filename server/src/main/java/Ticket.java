import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    private int ticketId;


    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;


    public Ticket() {

    }

    public int getId() {
        return ticketId;
    }

    public void setId(int id) {
        this.ticketId = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassengerId(Passenger p) {
        this.passenger = p;
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
                "id=" + ticketId +
                ", tarin_id='" + train.getId() + '\'' +
                ", tarin_number='" + train.getNumber() + '\'' +
                ", passanger_name='" + passenger.getName() +
                ", date='" + date +
                '}';
    }
}
