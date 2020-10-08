package com.github.nikel90.bankapi.data.transfer;

public class AccountDto {
    private long id;
    private int accountNumber;
    private long userId;

    public AccountDto(long id, int accountNumber, long userId) {
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
}
