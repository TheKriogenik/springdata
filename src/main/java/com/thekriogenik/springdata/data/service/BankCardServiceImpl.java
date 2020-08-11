package com.thekriogenik.springdata.data.service;

import com.thekriogenik.springdata.data.entities.BankCard;
import com.thekriogenik.springdata.data.entities.UserAccount;
import com.thekriogenik.springdata.data.repository.BankCardRepository;
import com.thekriogenik.springdata.domain.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository repository;

    @Autowired
    public BankCardServiceImpl(BankCardRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankCard createCard(UserAccount user) {
        var card = new BankCard(user);
        return repository.save(card);

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankCard update(BankCard card) {
        return repository.save(card);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<BankCard> findCard(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeCard(BankCard card) {
        repository.delete(card);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<BankCard> getAll() {
        var splt = repository.findAll().spliterator();
        return StreamSupport.stream(splt, false).collect(Collectors.toList());
    }
}
