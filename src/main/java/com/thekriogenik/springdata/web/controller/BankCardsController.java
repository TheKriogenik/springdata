package com.thekriogenik.springdata.web.controller;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.domain.usecase.AddCashUseCase;
import com.thekriogenik.springdata.domain.usecase.AddNewCardToUserUseCase;
import com.thekriogenik.springdata.domain.usecase.MakeTransactionUseCase;
import com.thekriogenik.springdata.domain.usecase.RemoveCardUseCase;
import com.thekriogenik.springdata.web.dto.BankCardDto;
import com.thekriogenik.springdata.web.mapper.DtoMapper;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class BankCardsController {

    private final AddNewCardToUserUseCase addCardUseCase;
    private final AddCashUseCase addCashUseCase;
    private final MakeTransactionUseCase transactionUseCase;
    private final RemoveCardUseCase removeUseCase;
    private final DtoMapper<BankCardDto, BankCard> mapper;

    public BankCardsController(AddNewCardToUserUseCase addCardUseCase,
                               MakeTransactionUseCase transactionUseCase,
                               AddCashUseCase addCashUseCase,
                               RemoveCardUseCase removeUseCase,
                               DtoMapper<BankCardDto, BankCard> mapper) {
        this.addCardUseCase = addCardUseCase;
        this.transactionUseCase = transactionUseCase;
        this.addCashUseCase = addCashUseCase;
        this.removeUseCase = removeUseCase;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    ResponseEntity<List<BankCardDto>> addCard(@PathVariable("userId") Integer userId) {
        return addCardUseCase
                .addNewCardToUser(userId)
                .map(ArrayList::new)
                .map(x -> x.stream().map(mapper::convert).collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());

    }

    @RequestMapping(value = "/{cardId}", method = RequestMethod.PUT)
    ResponseEntity<BankCardDto> addMoney(
            @PathVariable("cardId") Integer cardId,
            @RequestParam("cash") Double cash) {
        return addCashUseCase
                .addCash(cardId, cash)
                .map(mapper::convert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Pair<BankCardDto, BankCardDto>> sendMoney(
            @RequestParam("from") Integer fromId,
            @RequestParam("to") Integer toId,
            @RequestParam("cash") Double cash) {
        return transactionUseCase
                .makeTransaction(fromId, toId, cash)
                .map(this::mapPair)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Boolean> deleteCard(
            @PathVariable("id") Integer id) {
        var res = removeUseCase.removeCard(id);
        if (res) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Pair<BankCardDto, BankCardDto> mapPair(Pair<BankCard, BankCard> pair) {
        return Pair.of(
                mapper.convert(pair.getFirst()),
                mapper.convert(pair.getSecond()));
    }

}
