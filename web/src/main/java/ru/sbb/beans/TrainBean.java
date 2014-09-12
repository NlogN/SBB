package ru.sbb.beans;

import java.io.Serializable;

public class TrainBean implements Serializable {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationResult() {
        return "Train with params: " + name;
    }

}