package ru.sbb.beans;


import javax.faces.event.ActionEvent;
import java.io.Serializable;


public class UserBean implements Serializable {
    private String enteredPassword;
    private String password = "123";

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }


    public String verifyUserRegistration() {
        if (enteredPassword == null) {
            return "login";
        } else {
            if (enteredPassword.equals(password)) {
                return "managePage";
            } else {
                return "repeatLogin";
            }
        }
    }

    public void clearEnteredPassword(ActionEvent event) {
        this.enteredPassword = null;
    }

}