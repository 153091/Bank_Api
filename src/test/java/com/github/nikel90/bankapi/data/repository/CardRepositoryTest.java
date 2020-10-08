package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.model.Card;
import com.github.nikel90.bankapi.data.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final CardRepository cardRepository = new CardRepository(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(CardRepositoryTest.class.getResource("/CreateTables.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testSave() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 35, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account account = new Account(401841913, user.getId());
        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);
        accountRepository.save(account);

        Card expectedCard = new Card(213124214, 121123, account.getId());

        final Card saved = cardRepository.save(expectedCard);
        final Card actual = cardRepository.getById(saved.getId());

        assertEqualsCard(expectedCard, actual);
    }

//    @Test
//    public void testGetAll() throws SQLException {
//        User user = new User("Bazhenov", "Nikita", 24);
//        User user1 = new User("Bazhenov", "Nikita", 24);
//        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
//        userRepository.save(user);
//        userRepository.save(user1);
//
//        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);
//        Account account = new Account();
//        account.setAccountNumber(1231231);
//        account.setUserId(user.getId());
//
//        Account account1 = new Account();
//        account.setAccountNumber(112231231);
//        account.setUserId(user1.getId());
//
//        accountRepository.save(account);
//        accountRepository.save(account1);
//
//        Card expectedCard = new Card(213124214, 121123, account.getId());
//        Card expectedCard1 = new Card(213124214, 121123, account1.getId());
//
//        cardRepository.save(expectedCard);
//        cardRepository.save(expectedCard1);
//
//        List<Card> actual = cardRepository.getAll();
//
//        boolean result = false;
//
//        if (actual.size() == 2) {
//            result = true;
//        }
//        assertEquals(result, true);
//    }


    @Test
    public void testGetById() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 35, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account account = new Account(401841913, user.getId());
        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);
        accountRepository.save(account);

        Card expectedCard = new Card(213124214, 121123, account.getId());

        final Card saved = cardRepository.save(expectedCard);
        final Card actual = cardRepository.getById(saved.getId());

        assertEqualsCard(expectedCard, actual);
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 35, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account account = new Account(401841913, user.getId());
        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);
        accountRepository.save(account);

        Card expectedCard = new Card(213124214, 121123, account.getId());
        cardRepository.save(expectedCard);

        Card card = new Card(expectedCard.getId(),213124214, 121123, account.getId());
        cardRepository.update(card);

        final Card actual = cardRepository.getById((card.getId()));

        assertEqualsCard(card, actual);
    }


    @Test
    public void testRemoveById() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 35, "nikel-90", "root");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);
        userRepository.save(user);

        Account account = new Account(401841913, user.getId());
        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);
        accountRepository.save(account);

        Card expectedCard = new Card(213124214, 121123, account.getId());
        cardRepository.save(expectedCard);

        cardRepository.removeById(expectedCard.getId());

        boolean result = false;

        try {
            cardRepository.getById(expectedCard.getId());
        } catch (SQLException ex) {
            result = true;
        } finally {
            assertEquals(result, true);
        }
    }

    private static void assertEqualsCard(Card expectedCard, Card actual) {
        assertEquals(expectedCard.getCardNumber(), actual.getCardNumber());
        assertEquals(expectedCard.getCardBalance(), actual.getCardBalance());
        assertEquals(expectedCard.getAccountId(), actual.getAccountId());
    }

}