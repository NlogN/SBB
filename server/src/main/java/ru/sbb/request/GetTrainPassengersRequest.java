package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetTrainPassengersRequest extends Request implements Serializable {
    private final int trainNum;

    public GetTrainPassengersRequest(int num){
        this.trainNum =num;
        type=RequestType.GET_TRAIN_PASSENGERS;
    }

    public int getTrainNum(){
        return trainNum;
    }

    public RequestType getType(){
        return type;
    }

    public String toString() {
        return "[" + trainNum + "] ";
    }
}
