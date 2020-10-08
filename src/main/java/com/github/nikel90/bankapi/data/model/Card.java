package com.github.nikel90.bankapi.data.model;

public class Card {
    private long id;
    private int cardNumber;
    private double cardBalance;
    private long accountId;

    public Card() {
    }

    public Card(int cardNumber, double cardBalance, long accountId) {
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
        this.accountId = accountId;
    }

    public Card(long id, int cardNumber, double cardBalance, long accountId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
