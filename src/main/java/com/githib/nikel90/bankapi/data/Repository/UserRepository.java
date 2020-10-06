package com.githib.nikel90.bankapi.data.Repository;

import com.githib.nikel90.bankapi.data.Model.User;
import java.sql.Connection;
import java.sql.Statement;

public class UserRepository {

    private final Connection connection;

    private static final String s = "";


    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getById(int id) {
        Statement statement = connection.createStatement();
        statement.executeQuery();
        return new User(); //?????
    }



}
