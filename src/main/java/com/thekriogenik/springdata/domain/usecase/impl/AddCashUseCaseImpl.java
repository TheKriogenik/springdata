package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.domain.service.BankCardService;
import com.thekriogenik.springdata.domain.usecase.AddCashUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AddCashUseCaseImpl implements AddCashUseCase {

    private final BankCardService service;

    @Autowired
    public AddCashUseCaseImpl(BankCardService service) {
        this.service = service;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<BankCard> addCash(int cardId, double cash) {
        return service.findCard(cardId)
                .map(c -> addMoney(c, cash))
                .map(service::update);
    }

    private BankCard addMoney(BankCard card, double money){
        card.addCash(money);
        return card;
    }

}
