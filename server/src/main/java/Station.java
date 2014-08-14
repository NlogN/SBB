import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @Column(name = "station_id")
    private int stationId;

    @Column(name = "name")
    private String name;


//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
//    private List<Ticket> ticketList;


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



    @Override
    public String toString() {
        return "Station{" +
                "id=" + stationId +
                ", name='" + name +
                '}';
    }
}
