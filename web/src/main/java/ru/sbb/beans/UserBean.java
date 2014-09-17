package ru.sbb.beans;


import ru.sbb.RegistrationService;

import javax.faces.event.ActionEvent;
import java.io.Serializable;


public class UserBean implements Serializable {
    private String enteredPassword;
   // private String password = "123";

    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

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
            if (registrationService.checkPassword(enteredPassword)) {
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