package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Account;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountRepositoryTest {
    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
    private static final String GET_ALL_ACCOUNT = "SELECT * FROM ACCOUNT";
    private static final String SAVE_ACCOUNT = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, USER_ID) VALUES (?, ?);";

    final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");


    @Test
    public void testCreatedDao() throws SQLException {

        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {

            AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);

            Account account = new Account(127731273, 1);
            accountRepository.saveAccount(account);

            Account account1 = accountRepository.getById(account.getId());

//            Assert.assertEq(account, account1);
        }


    }
}
