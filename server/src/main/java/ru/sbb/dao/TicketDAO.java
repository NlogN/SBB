package ru.sbb.dao;

import ru.sbb.entity.Passenger;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface TicketDAO {

    public boolean buyTicket(int trainNum, String stationName, Passenger passenger, java.util.Date dateOfRace);

}
