package com.thekriogenik.springdata.data.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "bank_cards")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cash")
    private double cash;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final UserAccount maintainer;

    protected BankCard() {
        maintainer = null;
    }

    public BankCard(UserAccount maintainer) {
        this.maintainer = maintainer;
    }

    public int getId() {
        return id;
    }

    public double getCash() {
        return cash;
    }

    public void removeCash(double cash) {
        this.cash -= cash;
    }

    public void addCash(double cash) {
        this.cash += cash;
    }

    public UserAccount getMaintainer() {
        return maintainer;
    }

}
