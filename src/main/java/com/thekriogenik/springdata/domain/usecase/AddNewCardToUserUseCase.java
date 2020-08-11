package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.BankCard;

import java.util.Optional;
import java.util.Set;

public interface AddNewCardToUserUseCase {

    Optional<Set<BankCard>> addNewCardToUser(int userId);

}
