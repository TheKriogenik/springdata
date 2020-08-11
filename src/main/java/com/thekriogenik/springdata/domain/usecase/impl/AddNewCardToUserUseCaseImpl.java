package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import com.thekriogenik.springdata.domain.usecase.AddNewCardToUserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AddNewCardToUserUseCaseImpl implements AddNewCardToUserUseCase {

    private final UserAccountService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AddNewCardToUserUseCaseImpl(UserAccountService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Set<BankCard>> addNewCardToUser(int userId) {
        try {
            return userService
                    .findUser(userId)
                    .map(this::addCard)
                    .map(userService::updateUser)
                    .map(UserAccount::getCards);
        } catch (Exception e) {
            log.error("Error occurred while adding a new BankCard to user with id=" + userId);
            return Optional.empty();
        }

    }

    private UserAccount addCard(UserAccount user) {
        user.addCard(new BankCard(user));
        return user;
    }

}
