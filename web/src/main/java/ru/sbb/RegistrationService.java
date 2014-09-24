package ru.sbb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * Date: 21.08.14
 */
public class RegistrationService {
    private String password;


    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String psw) {
        if(psw!=null){
            return psw.equals(password);
        }else{
            return false;
        }
    }
}
