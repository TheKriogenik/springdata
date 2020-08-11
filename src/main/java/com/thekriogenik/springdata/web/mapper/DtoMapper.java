package com.thekriogenik.springdata.web.mapper;

public interface DtoMapper<DTO, ENTITY> {

    DTO convert(ENTITY entity);

}
