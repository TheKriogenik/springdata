package com.thekriogenik.springdata.domain.service;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.data.entities.UserAccount;

import java.util.List;
import java.util.Optional;

public interface BankCardService {

    BankCard createCard(UserAccount user);

    BankCard update(BankCard card);

    Optional<BankCard> findCard(int id);

    void removeCard(BankCard card);

    List<BankCard> getAll();

}
