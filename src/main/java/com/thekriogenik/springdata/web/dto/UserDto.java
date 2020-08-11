package com.thekriogenik.springdata.web.dto;

import java.util.List;

public class UserDto {

    private final int id;
    private final String name;
    private final List<BankCardDto> cards;

    public UserDto(int id, String name, List<BankCardDto> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BankCardDto> getCards() {
        return cards;
    }

}
