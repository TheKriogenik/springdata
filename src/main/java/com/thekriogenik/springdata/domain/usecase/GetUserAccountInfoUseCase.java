package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.UserAccount;

import java.util.Optional;

public interface GetUserAccountInfoUseCase {

    Optional<UserAccount> getUserAccountInfo(int id);

}
