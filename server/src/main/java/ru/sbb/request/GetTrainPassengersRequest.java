package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetTrainPassengersRequest extends Request implements Serializable {
    private final int trainNum;
    private String psw;

    public GetTrainPassengersRequest(int num, String psw){
        this.trainNum =num;
        this.psw = psw;
        type=RequestType.GET_TRAIN_PASSENGERS;
    }

    public int getTrainNum(){
        return trainNum;
    }

    public String getPassword(){
        return psw;
    }

    public RequestType getType(){
        return type;
    }

    public String toString() {
        return "[" + trainNum + "] ";
    }
}
