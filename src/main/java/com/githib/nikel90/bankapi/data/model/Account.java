package com.githib.nikel90.bankapi.data.model;

public class Account {
    private int id;
    private int accountNumber;
    private int UsersId;

//    Constuctor??

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getIdUsers() {
        return UsersId;
    }

    public void setIdUsers(int idUsers) {
        this.UsersId = UsersId;
    }
}
