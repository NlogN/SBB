package ru.sbb.beans;


public class TicketBean {
    private String name;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationResult() {
        return "train " + num + " passenger " + name + " buy ticket operation result";
    }

}