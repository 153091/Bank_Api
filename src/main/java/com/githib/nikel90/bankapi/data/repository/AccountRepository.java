package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountRepository {
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String GET_ALL_ACCOUNT = "SELECT * FROM USER"; // ;??????????
    private static final String SAVE_ACCOUNT = "";

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
                if(accounts.isEmpty()) {
                    return accounts.get(0);
                }
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public List<Account> getAllUsers(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCOUNT)) {

            try (ResultSet resultSet = statement.executeQuery(GET_ALL_ACCOUNT)) {
                return  toListAccount(resultSet);
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public boolean saveUsers(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_ACCOUNT)) {

            try(ResultSet resultSet = statement.executeQuery(SAVE_ACCOUNT)) {
                return true;  //todo: true or false
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
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

