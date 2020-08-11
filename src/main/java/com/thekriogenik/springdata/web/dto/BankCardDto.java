package com.thekriogenik.springdata.web.dto;

public class BankCardDto {

    private int id;
    private double cash;

    public BankCardDto(int id, double cash) {
        this.id = id;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public double getCash() {
        return cash;
    }
}
