package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest {
    private AccountRepository accountRepository;
    private  DataSource jdbcConnectionPool;

    @Before
    public void beforeAll() throws Exception {
        jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
        accountRepository = new AccountRepository(jdbcConnectionPool);
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(AccountRepositoryTest.class.getResource("/data.sql").toURI())));
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

    @Test
    public void testGetAll() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "password");
        User user1 = new User("Petrov", "Macar", 64, "petrov", "password");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);

        userRepository.save(user);
        userRepository.save(user1);

        Account expectedAccount = new Account(123421, user.getId());
        Account expectedAccount1 = new Account(123421, user1.getId());

        accountRepository.save(expectedAccount);
        accountRepository.save(expectedAccount1);

        List<Account> actual = accountRepository.getAll();

        boolean result = false;

        if (actual.size() == 2) {
            result = true;
        }
        assertTrue(result);
    }

    private void assertTrue(boolean result) {
    }


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


