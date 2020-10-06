package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;

import javax.sql.DataSource;
import java.sql.*;


public class UserRepository {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM USER"; // ;??????????
    private static final String SAVE_USERS = "";

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User getById(int id)  {
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setLong(id, 0);

            try (ResultSet resultSet = statement.executeQuery(GET_USER_BY_ID)) {

            }

        } catch (SQLException ex) {

        }
        return null;
    }


    public User getAll(User user) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
//            statement.executeQuery(GET_ALL);
            return user;
        } catch (SQLException ex) {

        } finally {
            //??????
        }
        return null;
    }


    public User save(User user) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
//            statement.executeQuery(SAVE);
            return user;
        } catch (SQLException ex) {

        } finally {
            //??????
        }
        return null;
    }

}
