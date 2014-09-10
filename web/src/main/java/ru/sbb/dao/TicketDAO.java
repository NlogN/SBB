package ru.sbb.dao;

import ru.sbb.entity.Passenger;
import ru.sbb.entity.Ticket;
import ru.sbb.entity.Train;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface TicketDAO {

    public void addTicket(Passenger newPassenger, Train train, java.util.Date dateOfRace);

    public List<Ticket> getTicketsByDayAndTrain(Train train, java.util.Date dateOfRace);

}
