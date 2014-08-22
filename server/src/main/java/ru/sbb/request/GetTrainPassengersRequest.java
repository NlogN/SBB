package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetTrainPassengersRequest extends Request  {
    private final int trainNum;
    private String psw;

    public GetTrainPassengersRequest(int num, String psw){
        super(RequestType.GET_TRAIN_PASSENGERS);
        this.trainNum =num;
        this.psw = psw;
    }

    public int getTrainNum(){
        return trainNum;
    }

    public String getPassword(){
        return psw;
    }



    public String toString() {
        return "[" + trainNum + "] ";
    }
}
