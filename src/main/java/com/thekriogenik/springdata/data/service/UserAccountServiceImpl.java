package com.thekriogenik.springdata.data.service;

import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.data.repository.UserAccountRepository;
import com.thekriogenik.springdata.domain.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserAccount createUser(String name) {
        var user = new UserAccount(name);
        return repository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserAccount updateUser(UserAccount user) {
        return repository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<UserAccount> findUser(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeUser(UserAccount user) {
        repository.delete(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<UserAccount> getAll() {
        var splt = repository.findAll().spliterator();
        return StreamSupport.stream(splt, false).collect(Collectors.toList());
    }
}
