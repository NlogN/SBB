package ru.sbb.beans;

public class TrainBean {
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