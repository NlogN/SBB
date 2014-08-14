import javax.persistence.*;
import java.util.Date;

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


    @Override
    public String toString() {
        return "Train{" +
                "id=" + train_id +
                ", number='" + number + '\'' +
                ", capacity='" + capacity +
                '}';
    }
}
