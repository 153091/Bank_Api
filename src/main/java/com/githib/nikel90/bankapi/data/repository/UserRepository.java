package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = ?;";
    private static final String GET_ALL_USERS = "SELECT * FROM USER;";
    private static final String SAVE_USER = "INSERT INTO USER (SURNAME, NAME, AGE) VALUES (?, ?, ?);";

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User getById(long id) throws SQLException {
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


    public List<User> getAll(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                return toListUser(resultSet);
            }
        }
    }


    public User save(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_USER,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getSurname());
            statement.setString(2, user.getName());
            statement.setInt(3, user.getAge());

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


    private List<User> toListUser(ResultSet resultSet) throws SQLException {
        List<User> ret = new ArrayList<>();

        while(resultSet.next()) {
            User user = new User();

            user.setId(resultSet.getLong("id"));
            user.setSurname(resultSet.getString("Surname"));
            user.setName(resultSet.getString("Name"));
            user.setAge(resultSet.getInt("Age"));

            ret.add(user);
        }
        return ret;
    }
}
