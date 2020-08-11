package com.thekriogenik.springdata.domain.usecase.impl;

import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import com.thekriogenik.springdata.domain.usecase.GetAllUsersUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {

    private final UserAccountService service;

    public GetAllUsersUseCaseImpl(UserAccountService service) {
        this.service = service;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<UserAccount> getAllUsers() {
        return service.getAll();
    }
}
