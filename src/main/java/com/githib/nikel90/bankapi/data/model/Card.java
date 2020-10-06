package com.githib.nikel90.bankapi.data.model;

public class Card {
    private long id;
    private int CardNumber;
    private int CardBalance;
    private long AccountId;


    //Constructor


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.CardNumber = CardNumber;
    }

    public int getCardBalance() {
        return CardBalance;
    }

    public void setCardBalance(int cardBalance) {
        this.CardBalance = CardBalance;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long AccountId) {
        this.AccountId = AccountId;
    }
}
