package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository {

    private final Connection connection;

    private static final String getById = "SELECT * FROM USER WHERE ID = ?";


    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getById(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(getById);
            return new User(); //?????
        }


    }


    public User getAll(User user) {

        return user;
    }

}
