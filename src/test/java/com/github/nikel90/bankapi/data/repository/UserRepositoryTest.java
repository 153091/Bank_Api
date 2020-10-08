package com.github.nikel90.bankapi.data.repository;

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

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private UserRepository userRepository;

    @Before
    public void before() throws Exception {
        DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
        userRepository = new UserRepository(jdbcConnectionPool);

        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(UserRepositoryTest.class.getResource("/data.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testSave() throws SQLException {
        User expectedUser = new User("Nikita", "Bazhenov", 24, "nikel-90", "root");

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById(saved.getId());

        assertEqualsUser(expectedUser, actual);

//        try (Connection connection = jdbcConnectionPool.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE ID = " + saved.getId())) {
//            try (final ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    final String actualName = resultSet.getString("name");
//                    final String actualSurname = resultSet.getString("surname");
//                    final int actualAge = resultSet.getInt("age");
//
//                    assertEquals(expectedUser.getName(), actualName);
//                    assertEquals(expectedUser.getSurname(), actualSurname);
//                    assertEquals(expectedUser.getAge(), actualAge);
//                } else {
//                 fail("Result set is empty.");
//                }
//            }
//        }
    }

    @Test
    public void testGetAll() throws SQLException {

        User user = new User("Bazhenov", "Nikita", 24,"nikel-90", "root");
        User user1 = new User("Kulakov", "Alexandr", 28,"kulakov", "root");

        userRepository.save(user);
        userRepository.save(user1);

        List<User> actual = userRepository.getAll();

        boolean result = false;

        if (actual.size() == 2) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void testGetById() throws SQLException {
        User expectedUser = new User("Bazhenov", "Nikita", 24,"nikel-90", "root");

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById((saved.getId()));

        assertEqualsUser(expectedUser, actual);
    }


    @Test
    public void testUpdate() throws SQLException {
        User expectedUser = new User("Bazhenov", "Nikita", 24, "nikel-90", "root");
        userRepository.save(expectedUser);

        User user = new User(expectedUser.getId(),"Bazhenov", "Sergey", 45, "sergei", "root");
        userRepository.update(user);

        final User actual = userRepository.getById((user.getId()));

        assertEqualsUser(user, actual);
    }

    @Test
    public void testRemoveById() throws SQLException {
        User expectedUser = new User("Nikita", "Bazhenov", 24, "nikel-90", "root");
        userRepository.save(expectedUser);

        userRepository.removeById(expectedUser.getId());

        boolean result = false;

       try {
           userRepository.getById(expectedUser.getId());
       } catch (SQLException ex) {
           result = true;
       } finally {
           assertEquals(result, true);
       }
    }

    private static void assertEqualsUser(User expectedUser, User actual) {
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getSurname(), actual.getSurname());
        assertEquals(expectedUser.getAge(), actual.getAge());
    }
}
