package com.githib.nikel90.bankapi.data.model;

public class Card {
    private int id;
    private int cardNumber;
    private int cardBalance;
    private int AccountId;


    //Constructor


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(int cardBalance) {
        this.cardBalance = cardBalance;
    }

    public int getIdAccount() {
        return AccountId;
    }

    public void setIdAccount(int idAccount) {
        this.AccountId = AccountId;
    }
}
