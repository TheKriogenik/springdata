package com.thekriogenik.springdata.web.mapper;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.web.dto.BankCardDto;
import com.thekriogenik.springdata.web.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoMapper implements DtoMapper<UserDto, UserAccount> {

    private final DtoMapper<BankCardDto, BankCard> cardMapper;

    public UserDtoMapper(DtoMapper<BankCardDto, BankCard> cardMapper) {
        this.cardMapper = cardMapper;
    }

    @Override
    public UserDto convert(UserAccount userAccount) {
        return new UserDto(
                userAccount.getId(),
                userAccount.getName(),
                userAccount
                        .getCards()
                        .stream()
                        .map(cardMapper::convert)
                        .collect(Collectors.toList()));
    }

}
