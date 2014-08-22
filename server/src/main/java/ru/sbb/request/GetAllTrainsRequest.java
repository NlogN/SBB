package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class GetAllTrainsRequest extends Request  {
    private String psw;

    public GetAllTrainsRequest(String psw){
        super(RequestType.GET_ALL_TRAIN_LIST);
        this.psw = psw;
    }

    public String getPassword(){
        return psw;
    }



}
