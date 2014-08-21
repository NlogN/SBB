package ru.sbb.dao;


import ru.sbb.entity.Train;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public interface TrainDAO {

    public void addTrain(Train train);

    public List<Train> getTrainOnRouteABList(java.util.Date lowerBound, java.util.Date upperBound, String stationAName, String stationBName);

    public Train getTrainById(int id);

}
