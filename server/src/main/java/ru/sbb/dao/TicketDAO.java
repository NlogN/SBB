package ru.sbb.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface TicketDAO {

    public boolean buyTicket(int trainNum, String stationName, String name, String surname, java.util.Date birthday, java.util.Date dateOfRace);

}
