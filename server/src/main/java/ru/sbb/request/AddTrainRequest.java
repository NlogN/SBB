package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class AddTrainRequest extends Request implements Serializable {
    private String psw;
    private int number;
    private int capacity;

    public AddTrainRequest(int number,int capacity, String psw){
        this.psw = psw;
        this.number=number;
        this.capacity=capacity;
        type=RequestType.ADD_TRAIN;
    }

    public String getPassword(){
        return psw;
    }
    public int getNumber(){
        return number;
    }
    public int getCapacity(){
        return capacity;
    }

    public RequestType getType(){
        return type;
    }

}
