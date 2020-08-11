package com.thekriogenik.springdata.data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<BankCard> cards;

    protected UserAccount() {
        cards = Set.of();
    }

    public UserAccount(String name) {
        this.name = name;
        this.cards = Set.of();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BankCard> getCards() {
        return cards;
    }

    public void addCard(BankCard card) {
        cards.add(card);
    }

    public void removeCard(BankCard card) {
        cards.remove(card);
    }
}
