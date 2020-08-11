package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import com.thekriogenik.springdata.domain.usecase.CreateNewUserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateNewUserUseCaseImp implements CreateNewUserUseCase {

    private final UserAccountService service;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CreateNewUserUseCaseImp(UserAccountService service) {
        this.service = service;
    }

    @Override
    public Optional<UserAccount> createNewUser(String name) {
        try {
            var res = service.createUser(name);
            return Optional.of(res);
        } catch (Exception e) {
            log.error("Error in creating new UserAccount for name=" + name);
            return Optional.empty();
        }
    }

}
