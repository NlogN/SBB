package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class Request implements Serializable {
    private final RequestType type;

    Request(RequestType type){
        this.type=type;
    }

    public RequestType getType(){
           return type;
    }

}
