package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static final DataSource jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test_mem", "sa", "");
    private static final UserRepository userRepository = new UserRepository(jdbcConnectionPool);


    @BeforeAll
    static void beforeAll() throws Exception {
        final String sqlCreateTables = String.join("\n", Files.readAllLines(Paths.get(UserRepositoryTest.class.getResource("/CreateTables.sql").toURI())));
        try (Connection connection = jdbcConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateTables)) {
            statement.executeUpdate();
        }
    }

    @Test
    public void testSave() throws SQLException {
        User expectedUser = new User();
        expectedUser.setName("Nikita");
        expectedUser.setSurname("Bazhenov");
        expectedUser.setAge(32);

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById(saved.getId());
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getSurname(), actual.getSurname());
        assertEquals(expectedUser.getAge(), actual.getAge());

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
    public void testGetById() throws SQLException {
        User expectedUser = new User();
        expectedUser.setName("Nikita");
        expectedUser.setSurname("Bazhenov");
        expectedUser.setAge(32);

        final User saved = userRepository.save(expectedUser);
        final User actual = userRepository.getById(0);
        assertEquals(expectedUser.getName(), actual.getName());
        assertEquals(expectedUser.getSurname(), actual.getSurname());
        assertEquals(expectedUser.getAge(), actual.getAge());
    }
}
