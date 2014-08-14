import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @Column(name = "train_id")
    private int train_id;

    @Column(name = "number")
    private int number;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private List<Ticket> ticketList;


    public Train() {

    }

    public int getId() {
        return train_id;
    }

    public void setId(int id) {
        this.train_id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

//    @Override
//    public String toString() {
//        return "Train{" +
//                "id=" + train_id +
//                ", number='" + number + '\'' +
//                ", capacity='" + capacity +
//                '}';
//    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Ticket ticket : ticketList) {
            sb.append(ticket.getPassenger().getName() + " ");
        }

        return "Train{" +
                "id=" + train_id +
                ", number='" + number + '\'' +
                ", capacity='" + capacity +
                ", ticketInfo='" + sb.toString() +
                '}';
    }
}
