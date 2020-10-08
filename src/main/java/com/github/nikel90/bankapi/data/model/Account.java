package com.github.nikel90.bankapi.data.model;

public class Account {
    private long id;
    private int accountNumber;
    private long userId;

    public Account() {
    }

    public Account(int accountNumber, long userId) {
        this.accountNumber = accountNumber;
        this.userId = userId;
    }

    public Account(long id, int accountNumber, long userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", userId=" + userId +
                '}';
    }
}
