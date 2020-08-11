package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.BankCard;

import java.util.Optional;

public interface AddCashUseCase {

    Optional<BankCard> addCash(int cardId, double cash);

}
