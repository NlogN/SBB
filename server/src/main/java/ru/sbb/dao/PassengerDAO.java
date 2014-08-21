package ru.sbb.dao;

import ru.sbb.entity.Passenger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface PassengerDAO {

    List<Passenger> getPassengersByTrain(int trainNum);
}
