package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM USER"; // ;??????????
    private static final String CREATE_USERS = "";

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User getById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {

            statement.setLong(id, 1);

            try (ResultSet resultSet = statement.executeQuery(GET_USER_BY_ID)) {

                List<User> users = toListUser(resultSet);

                if (users.isEmpty()) {
                    return users.get(0);
                }
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public List<User> getAllUsers(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {

            try (ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
                return toListUser(resultSet);
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public User createUsers(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_USERS)) {

            int index = statement.executeUpdate(CREATE_USERS);

            if (index == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    return user;
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            throw ex;
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
