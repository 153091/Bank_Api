package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Account;
import com.githib.nikel90.bankapi.data.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.DeleteDbFiles;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(AccountRepositoryTest.class.getResource("/CreateTables.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }


    @Test
    public void testSave() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account expectedAccount = new Account();
        expectedAccount.setAccountNumber(1231231);
        expectedAccount.setUserId(user.getId());


        final Account saved = accountRepository.save(expectedAccount);
        final Account actual = accountRepository.getById(saved.getId());

        assertEqualsAccount(expectedAccount, actual);
    }

//    @Test
//    public void testGetAll() throws SQLException {
//        User user = new User("Bazhenov", "Nikita", 24);
//        User user1 = new User("Petrov", "Macar", 64);
//        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
//
//        userRepository.save(user);
//        userRepository.save(user1);
//
//        Account expectedAccount = new Account();
//        expectedAccount.setAccountNumber(1231231);
//        expectedAccount.setUserId(user.getId());
//
//        Account expectedAccount1 = new Account();
//        expectedAccount.setAccountNumber(112231231);
//        expectedAccount.setUserId(user1.getId());
//
//        accountRepository.save(expectedAccount);
//        accountRepository.save(expectedAccount1);
//
//        List<Account> actual = accountRepository.getAll();
//
//        boolean result = false;
//
//        if (actual.size() == 2) {
//            result = true;
//        }
//        assertTrue(result);
//    }


    @Test
    public void testGetId() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account expectedAccount = new Account();
        expectedAccount.setAccountNumber(1231231);
        expectedAccount.setUserId(user.getId());


        final Account saved = accountRepository.save(expectedAccount);
        final Account actual = accountRepository.getById(saved.getId());

        assertEqualsAccount(expectedAccount, actual);
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account expectedAccount = new Account(123123,user.getId());
        accountRepository.save(expectedAccount);

        Account account = new Account(expectedAccount.getId(), 123111, user.getId());
        accountRepository.update(account);

        final Account actual = accountRepository.getById((account.getId()));

        assertEqualsAccount(account, actual);
    }


    @Test
    public void testRemoveById() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account expectedAccount = new Account(123123,user.getId());
        accountRepository.save(expectedAccount);

        accountRepository.removeById(expectedAccount.getId());

        boolean result = false;

        try {
            accountRepository.getById(expectedAccount.getId());
        } catch (SQLException ex) {
            result = true;
        } finally {
            assertEquals(result, true);
        }
    }


    private static void assertEqualsAccount(Account expectedAccount, Account actual) {
        assertEquals(expectedAccount.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expectedAccount.getUserId(), actual.getUserId());
    }


}


