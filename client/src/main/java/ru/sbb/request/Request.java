package ru.sbb.request;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 19.08.14
 */
public abstract class Request{
    protected RequestType type;

    public RequestType getType(){
           return type;
    }

}
