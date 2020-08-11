package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.BankCard;
import org.springframework.data.util.Pair;

import java.util.Optional;

public interface MakeTransactionUseCase {

    Optional<Pair<BankCard, BankCard>> makeTransaction(int firstCardId, int secondCardId, double cash);

}
