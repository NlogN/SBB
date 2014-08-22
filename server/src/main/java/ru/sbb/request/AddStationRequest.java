package ru.sbb.request;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 19.08.14
 */
public class AddStationRequest extends Request{
    private String psw;
    private String name;


    public AddStationRequest(String name, String psw){
        super(RequestType.ADD_STATION);
        this.psw = psw;
        this.name=name;
    }

    public String getPassword(){
        return psw;
    }

    public String getName(){
        return name;
    }


}
