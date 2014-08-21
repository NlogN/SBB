package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetAllTrainsRequest extends Request implements Serializable {
    private String psw;

    public GetAllTrainsRequest(String psw){
        this.psw = psw;
        type=RequestType.GET_ALL_TRAIN_LIST;
    }

    public String getPassword(){
        return psw;
    }

    public RequestType getType(){
        return type;
    }

}
