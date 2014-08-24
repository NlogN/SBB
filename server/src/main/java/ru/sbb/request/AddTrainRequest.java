package ru.sbb.request;



/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class AddTrainRequest extends Request {
    private String psw;
    private int number;
    private int capacity;

    public AddTrainRequest(int number, int capacity, String psw) {
        super(RequestType.ADD_TRAIN);
        this.psw = psw;
        this.number = number;
        this.capacity = capacity;
    }

    public String getPassword() {
        return psw;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }


}
