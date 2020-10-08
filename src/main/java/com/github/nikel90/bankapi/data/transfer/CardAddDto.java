package com.github.nikel90.bankapi.data.transfer;

public class CardAddDto {
    private long cardId;
    private double amount;

    public CardAddDto(long cardId, double amount) {
        this.cardId = cardId;
        this.amount = amount;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
