package com.thekriogenik.springdata.web.controller;

import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.domain.usecase.CreateNewUserUseCase;
import com.thekriogenik.springdata.domain.usecase.GetAllUsersUseCase;
import com.thekriogenik.springdata.domain.usecase.GetUserAccountInfoUseCase;
import com.thekriogenik.springdata.web.dto.UserDto;
import com.thekriogenik.springdata.web.mapper.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserAccountController {

    private final GetUserAccountInfoUseCase getOneUseCase;
    private final GetAllUsersUseCase getAllUseCase;
    private final CreateNewUserUseCase createUseCase;

    private final DtoMapper<UserDto, UserAccount> mapper;

    public UserAccountController(GetUserAccountInfoUseCase getOneUseCase,
                                 GetAllUsersUseCase getAllUseCase,
                                 CreateNewUserUseCase createUseCase,
                                 DtoMapper<UserDto, UserAccount> mapper) {
        this.getOneUseCase = getOneUseCase;
        this.getAllUseCase = getAllUseCase;
        this.createUseCase = createUseCase;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") Integer id) {
        return getOneUseCase
                .getUserAccountInfo(id)
                .map(mapper::convert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserDto> getAllUsers() {
        return getAllUseCase
                .getAllUsers()
                .stream()
                .map(mapper::convert)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<UserDto> createNewUser(@RequestParam("name") String name) {
        return createUseCase
                .createNewUser(name)
                .map(mapper::convert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());

    }

}
