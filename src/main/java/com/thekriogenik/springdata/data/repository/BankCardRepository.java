package com.thekriogenik.springdata.data.repository;

import com.thekriogenik.springdata.data.entities.BankCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends CrudRepository<BankCard, Integer> {
}
