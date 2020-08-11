package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.service.BankCardService;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import com.thekriogenik.springdata.domain.usecase.RemoveCardUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Component
public class RemoveCardUseCaseImpl implements RemoveCardUseCase {

    private final BankCardService cardService;
    private final UserAccountService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RemoveCardUseCaseImpl(BankCardService cardService,
                                 UserAccountService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean removeCard(int cardId) {
        try {
            cardService
                    .findCard(cardId)
                    .map(this::pairWithUser)
                    .ifPresent(this::remove);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while deleting card with id=" + cardId);
            return false;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private void remove(Pair<BankCard, UserAccount> pair) {
        pair.getSecond().removeCard(pair.getFirst());
        userService.updateUser(pair.getSecond());
    }

    private Pair<BankCard, UserAccount> pairWithUser(BankCard card) {
        return Pair.of(card, card.getMaintainer());
    }

}
