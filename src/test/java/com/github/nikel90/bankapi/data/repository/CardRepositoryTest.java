package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.model.Card;
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

public class CardRepositoryTest {
    private CardRepository cardRepository;
    private  DataSource jdbcConnectionPool;

    @Before
    public void beforeAll() throws Exception {
        jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
        cardRepository = new CardRepository(jdbcConnectionPool);
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(CardRepositoryTest.class.getResource("/data.sql").toURI())));
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

    @Test
    public void testGetAll() throws SQLException {
        User user = new User("Bazhenov", "Nikita", 24, "nikel-90", "password");
        User user1 = new User("Petrov", "Macar", 64, "petrov", "password");
        UserRepository userRepository = new UserRepository(jdbcConnectionPool);

        userRepository.save(user);
        userRepository.save(user1);

        Account account = new Account(123421, user.getId());
        Account account1 = new Account(123421, user1.getId());
        AccountRepository accountRepository = new AccountRepository(jdbcConnectionPool);

        accountRepository.save(account);
        accountRepository.save(account1);

        Card expectedCard = new Card(213124214, 121123, account.getId());
        Card expectedCard1 = new Card(213124214, 121123, account1.getId());

        cardRepository.save(expectedCard);
        cardRepository.save(expectedCard1);

        List<Card> actual = cardRepository.getAll();

        boolean result = false;

        if (actual.size() == 2) {
            result = true;
        }
        assertEquals(result, true);
    }


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