package ru.sbb.dao;

import ru.sbb.entity.Passenger;
import ru.sbb.entity.Train;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface PassengerDAO {

    List<Passenger> getPassengersByTrain(int trainNum);

    List<Passenger> getPassengersByInfo(Train train, String name, String surname, java.util.Date birthday);
}
