package com.kszczep5;

public class Receipt {
    private String name;
    private int amount;

    public Receipt(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public Receipt() {

    }

    public int getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }
}
