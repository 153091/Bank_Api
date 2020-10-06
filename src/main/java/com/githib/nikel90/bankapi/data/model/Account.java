package com.githib.nikel90.bankapi.data.model;

public class Account {
    private long id;
    private int AccountNumber;
    private long UsersId;

    public Account() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.AccountNumber = accountNumber;
    }

    public long getUsersId() {
        return UsersId;
    }

    public void setUsersId(long UsersId) {
        this.UsersId = UsersId;
    }
}
