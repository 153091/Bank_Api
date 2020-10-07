package com.githib.nikel90.bankapi.data.transfer;

public class CardDto {
    private final long id;
    private final int cardNumber;
    private final int cardBalance;
    private final long accountId;

    public CardDto(long id, int cardNumber, int cardBalance, long accountId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
        this.accountId = accountId;
    }
}
