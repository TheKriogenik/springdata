package com.thekriogenik.springdata.domain.usecase;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.domain.service.BankCardService;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class MakeTransactionUseCaseTest {

    @Autowired
    private MakeTransactionUseCase useCase;

    @Autowired
    private BankCardService cardService;

    @Autowired
    private UserAccountService userService;

    private BankCard cardWithMoney;
    private BankCard cardWithoutMoney;

    @BeforeEach
    void before() {
        var user = userService.createUser("test");
        cardWithMoney = cardService.createCard(user);
        cardWithMoney.addCash(100);
        cardService.update(cardWithMoney);
        cardWithoutMoney = cardService.createCard(user);
    }

    @AfterEach
    void after(){
        userService.getAll().forEach(userService::removeUser);
        assert (cardService.getAll().isEmpty());
    }

    @Test
    void ifCardsExist_correctTransaction(){
        var res = useCase
                .makeTransaction(cardWithMoney.getId(), cardWithoutMoney.getId(), 50);

        var firstCard = cardService.findCard(cardWithMoney.getId());
        var secondCard = cardService.findCard(cardWithMoney.getId());

        Assertions.assertTrue(res.isPresent());
        Assertions.assertEquals(firstCard.map(BankCard::getCash), Optional.of(50.));
        Assertions.assertEquals(secondCard.map(BankCard::getCash), Optional.of(50.));
    }

    @Test
    void ifNotEnoughMoney_rollback(){
        var res = useCase
                .makeTransaction(cardWithoutMoney.getId(), cardWithMoney.getId(), 50);

        var firstCard = cardService.findCard(cardWithoutMoney.getId());
        var secondCard = cardService.findCard(cardWithMoney.getId());

        Assertions.assertTrue(res.isEmpty());
        Assertions.assertEquals(firstCard.map(BankCard::getCash), Optional.of(0.));
        Assertions.assertEquals(secondCard.map(BankCard::getCash), Optional.of(100.));
    }

}
