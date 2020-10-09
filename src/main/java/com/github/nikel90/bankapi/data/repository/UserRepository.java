package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements CrudRepository<User, Long> {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = ?;";
    private static final String GET_ALL_USERS = "SELECT * FROM USER;";
    private static final String SAVE_USER = "INSERT INTO USER (SURNAME, NAME, AGE, LOGIN, PASSWORD) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE USER SET SURNAME = ?, NAME = ?, AGE = ?, LOGIN = ?, PASSWORD = ?;";
    private static final String DELETE_USER = "DELETE FROM USER WHERE ID = ?;";

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<User> users = toListUser(resultSet);

                if (!users.isEmpty()) {
                    return users.get(0);
                } else {
                    throw new SQLException("User not found.");
                }
            }
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return toListUser(resultSet);
            }
        }
    }

    @Override
    public boolean removeById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }

    @Override
    public User save (User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_USER,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getSurname());
            statement.setString(2, user.getName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    return user;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getSurname());
            statement.setString(2, user.getName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());

            statement.execute();
        }
    }


    private List<User> toListUser(ResultSet resultSet) throws SQLException {
        List<User> ret = new ArrayList<>();

        while(resultSet.next()) {
            User user = new User();

            user.setId(resultSet.getLong("id"));
            user.setSurname(resultSet.getString("surname"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));

            ret.add(user);
        }
        return ret;
    }
}
