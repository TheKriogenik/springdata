package com.thekriogenik.springdata.domain.service;

import com.thekriogenik.springdata.data.entities.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {

    UserAccount createUser(String name);

    UserAccount updateUser(UserAccount user);

    Optional<UserAccount> findUser(int id);

    void removeUser(UserAccount user);

    List<UserAccount> getAll();

}
