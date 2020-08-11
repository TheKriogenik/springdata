package com.thekriogenik.springdata.data.repository;

import com.thekriogenik.springdata.data.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
}
