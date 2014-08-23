package ru.sbb.request;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 20.08.14
 */
import java.io.Serializable;


public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String text;

    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return text;
    }

}