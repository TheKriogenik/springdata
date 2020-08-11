package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import com.thekriogenik.springdata.domain.usecase.GetUserAccountInfoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetUserAccountInfoUseCaseImpl implements GetUserAccountInfoUseCase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserAccountService service;

    @Autowired
    public GetUserAccountInfoUseCaseImpl(UserAccountService service) {
        this.service = service;
    }

    @Override
    public Optional<UserAccount> getUserAccountInfo(int id) {
        return service.findUser(id);
    }
}
