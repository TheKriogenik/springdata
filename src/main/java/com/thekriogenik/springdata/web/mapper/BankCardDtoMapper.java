package com.thekriogenik.springdata.web.mapper;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.web.dto.BankCardDto;
import org.springframework.stereotype.Component;

@Component
public class BankCardDtoMapper implements DtoMapper<BankCardDto, BankCard> {

    @Override
    public BankCardDto convert(BankCard bankCard) {
        return new BankCardDto(bankCard.getId(), bankCard.getCash());
    }

}
