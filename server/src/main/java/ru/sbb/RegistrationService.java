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
    private static String password;

    public RegistrationService(){
        Scanner in = null;
        try {
            in = new Scanner(new File("psw.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(in.hasNextLine()){
            password =  in.nextLine();
        }
    }


    public boolean checkPassword(String psw){
        return psw.equals(password);
    }
}
