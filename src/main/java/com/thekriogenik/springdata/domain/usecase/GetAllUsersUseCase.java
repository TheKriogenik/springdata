package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.UserAccount;

import java.util.List;

public interface GetAllUsersUseCase {

    List<UserAccount> getAllUsers();
}
