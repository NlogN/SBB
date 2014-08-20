package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class Request implements Serializable {
    protected RequestType type;

    public RequestType getType(){
           return type;
    }

}
