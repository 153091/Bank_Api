package com.github.nikel90.bankapi.data.transfer;

public class AccountDto {
    private final long id;
    private final int accountNumber;
    private final long userId;

    public AccountDto(long id, int accountNumber, long userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.userId = userId;
    }
}
