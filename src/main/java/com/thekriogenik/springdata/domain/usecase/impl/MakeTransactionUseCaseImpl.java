package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.domain.service.BankCardService;
import com.thekriogenik.springdata.domain.usecase.MakeTransactionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class MakeTransactionUseCaseImpl implements MakeTransactionUseCase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BankCardService service;

    @Value("${bank.trashold}")
    private double trashHold;

    public MakeTransactionUseCaseImpl(BankCardService service) {
        this.service = service;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Optional<Pair<BankCard, BankCard>> makeTransaction(int firstCardId, int secondCardId, double cash) {
        log.debug("Started transaction");
        try {
            return service
                    .findCard(firstCardId)
                    .flatMap(first -> addSecondCard(first, secondCardId))
                    .flatMap(pair -> changeCash(pair, cash))
                    .flatMap(this::updateCards);
        } catch (Exception e) {
            log.error("Error occurred while making transaction of cash for cards id1="
                    + firstCardId + " and id2=" + secondCardId);
            return Optional.empty();
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private Optional<Pair<BankCard, BankCard>> changeCash(Pair<BankCard, BankCard> cards, double cash) {
        if ((cards.getFirst().getCash() - cash) > trashHold) {
            cards.getFirst().removeCash(cash);
            cards.getSecond().addCash(cash);
            return Optional.of(cards);
        } else {
            return Optional.empty();
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private Optional<Pair<BankCard, BankCard>> addSecondCard(BankCard first, int secondCardId) {
        return service.findCard(secondCardId).map(c -> Pair.of(first, c));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private Optional<Pair<BankCard, BankCard>> updateCards(Pair<BankCard, BankCard> cards) {
        var first = service.update(cards.getFirst());
        var second = service.update(cards.getSecond());
        return Optional.of(Pair.of(first, second));
    }

}

/* Kotlin

    return service.findCard(firstCardId).flatMap { firstCard ->
        service.findCard(secondCardId).flatMap { secondCard ->
            when(firstCard.cash >= cash) {
                true -> {
                    firstCard.cash -= cash
                    secondCard.cash += cash
                    Optional.of(service.update(firstCard) to service.update(secondCard))
                }
            }
        }
    }

 */
