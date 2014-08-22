package ru.sbb.request;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class OverRequest extends Request implements Serializable {
    private final String text;

    public OverRequest(String text){
        super(RequestType.OVER);
        this.text=text;
    }

    public String getText(){
        return text;
    }


    public String toString() {
        return "[" + text + "] ";
    }
}
