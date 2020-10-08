package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.Account;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements CrudRepository <Account, Long> {
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?;";
    private static final String GET_ALL_ACCOUNT = "SELECT * FROM ACCOUNT;";
    private static final String SAVE_ACCOUNT = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, USER_ID) VALUES (?, ?);";
    private final static String UPDATE_ACCOUNT = "UPDATE ACCOUNT SET ACCOUNT_NUMBER = ?, USER_ID = ?;";
    private final static String DELETE_ACCOUNT = "DELETE FROM ACCOUNT WHERE ID = ?;";

    private final DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Account getById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<Account> accounts = toListAccount(resultSet);

                if (!accounts.isEmpty()) {
                    return accounts.get(0);
                } else {
                    throw new SQLException("Account not found.");
                }
            }
        }
    }

    @Override
    public List<Account> getAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNT)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                return toListAccount(resultSet);
            }
        }
    }

    @Override
    public boolean removeById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }

    @Override
    public Account save(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_ACCOUNT,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, account.getAccountNumber());
            statement.setLong(2, account.getUserId());

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

    @Override
    public void update(Account account) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {

            statement.setInt(1, account.getAccountNumber());
            statement.setLong(2, account.getUserId());

            statement.execute();
        }
    }


    private List<Account> toListAccount(ResultSet resultSet) throws SQLException {
        List<Account> ret = new ArrayList<>();

            while(resultSet.next()) {
                Account account = new Account();

                account.setId(resultSet.getLong("id"));
                account.setAccountNumber(resultSet.getInt("account_number"));
                account.setUserId(resultSet.getLong("user_id"));

                ret.add(account);
            }
        return ret;
    }
}

