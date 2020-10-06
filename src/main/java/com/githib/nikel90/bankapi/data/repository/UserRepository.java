package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class UserRepository {
    private static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM USER"; // ;??????????
    private static final String SAVE = "";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User getById(int id)  {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(GET_USER_BY_ID);
            return new User(); //????????
        } catch (SQLException ex) {

        } finally {
            //??????
        }
    }


    public User getAll(User user) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(GET_ALL);
            return user;
        } catch (SQLException ex) {

        } finally {
            //??????
        }
    }


    public User save(User user) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(SAVE);
            return user;
        } catch (SQLException ex) {

        } finally {
            //??????
        }
    }

}
