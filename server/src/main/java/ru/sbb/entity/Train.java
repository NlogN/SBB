package ru.sbb.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @Column(name = "train_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int trainId;

    @Column(name = "number")
    private int number;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private List<Ticket> ticketList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private List<ScheduleRecord> scheduleList;


    public Train() {

    }

    public int getId() {
        return trainId;
    }

    public void setId(int id) {
        this.trainId = id;
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

    public List<ScheduleRecord> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleRecord> scheduleList) {
        this.scheduleList = scheduleList;
    }

//    @Override
//    public String toString() {
//        return "ru.sbb.entity.Train{" +
//                "id=" + train_id +
//                ", number='" + number + '\'' +
//                ", capacity='" + capacity +
//                '}';
//    }

//    @Override
//    public String toString() {
//        StringBuffer sb = new StringBuffer();
//        for (ru.sbb.entity.TicketDAO ticket : ticketList) {
//            sb.append(ticket.getPassenger().getName() + " ");
//        }
//
//        return "ru.sbb.entity.Train{" +
//                "id=" + trainId +
//                ", number='" + number + '\'' +
//                ", capacity='" + capacity +
//                ", ticketInfo='" + sb.toString() +
//                '}';
//    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (ScheduleRecord schedule : scheduleList) {
            sb.append(schedule.getStation().getName() + " ");
        }

        return "ru.sbb.entity.Train{" +
                "id=" + trainId +
                ", number='" + number + '\'' +
                ", capacity='" + capacity +
                ", stationList='" + sb.toString() +
                '}';
    }
}
