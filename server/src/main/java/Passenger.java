import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @Column(name = "passenger_id")
    private int passengerId;


    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passenger")
    private List<Ticket> ticketList;

    public Passenger() {

    }

    public int getId() {
        return passengerId;
    }

    public void setId(int id) {
        this.passengerId = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname1) {
        this.surname = surname;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + passengerId +
                ", name='" + name + '\'' +
                ", surname='" + surname +
                '}';
    }
}
