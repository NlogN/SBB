package ru.sbb.beans;


import ru.sbb.RegistrationService;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;
import java.io.Serializable;


public class UserBean implements Serializable {
    private String enteredPassword;
   // private String operationResult = "no change";
   private String operationResult = "";
    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

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
            return "/login/login";
        } else {
            if (registrationService.checkPassword(enteredPassword)) {
                return "/manage/managePage";
            } else {
                return "/login/repeatLogin";
            }
        }
    }

    public void clearEnteredPassword(ActionEvent event) {
        this.enteredPassword = null;
    }

    public void checkAlreadyLoggedin() throws IOException {
        setOperationResult("to index");

        FacesContext fc = FacesContext.getCurrentInstance();
      //  if (!registrationService.checkPassword(enteredPassword)) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler)
                    fc.getApplication().getNavigationHandler();

            nav.performNavigation("index");
       // }
    }

}