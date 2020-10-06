package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Account;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepository {
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
    private static final String GET_ALL_ACCOUNT = "SELECT * FROM ACCOUNT";
    private static final String CREATE_ACCOUNT = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, USER_ID) VALUES (?, ?);";

    private final DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Account getById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {

            statement.setLong(id, 1);

            try (ResultSet resultSet = statement.executeQuery(GET_ACCOUNT_BY_ID)) {
                List<Account> accounts = toListAccount(resultSet);
                if (accounts.isEmpty()) {
                    return accounts.get(0);
                } else {
                    throw new SQLException("User not found.");
                }
            }
        }
    }


    public List<Account> getAllUsers(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNT)) {

            try (ResultSet resultSet = statement.executeQuery(GET_ALL_ACCOUNT)) {
                return toListAccount(resultSet);
            }
        }
    }


    public Account createAccount(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT,
                     Statement.RETURN_GENERATED_KEYS)) {

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
                    return account;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    private List<Account> toListAccount(ResultSet resultSet) throws SQLException {
        List<Account> ret = new ArrayList<>();

            while(resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong("id"));
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setUsersId(resultSet.getLong("UserId"));
            }
        return ret;
    }
}

