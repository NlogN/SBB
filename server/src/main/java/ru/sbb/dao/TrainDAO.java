package ru.sbb.dao;


import ru.sbb.entity.Train;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface TrainDAO {

    public void addTrain(int number, int capacity);

    public List<Train> getTrainByRoute(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName);

    public List<Train> getTrains();

    public List<Train> getTrainByNum(int trainNum);

}
