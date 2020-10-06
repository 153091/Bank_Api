package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private static final String GET_CARD_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String GET_ALL_CARD = "SELECT * FROM USER"; // ;??????????
    private static final String SAVE_CARD = "";

    private final DataSource dataSource;

    public CardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Card getById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_ID)) {

            statement.setLong(id, 1);

            try (ResultSet resultSet = statement.executeQuery(GET_CARD_BY_ID)) {
                List<Card> card = toListCard(resultSet);
                if(card.isEmpty()) {
                    return card.get(0);
                }
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public List<Card> getAllUsers(Card card) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CARD)) {

            try (ResultSet resultSet = statement.executeQuery(GET_ALL_CARD)) {
                return  toListCard(resultSet);
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public boolean saveUsers(Card card) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_CARD)) {

            try(ResultSet resultSet = statement.executeQuery(SAVE_CARD)) {
                return true;  //todo: true or false
            } catch (SQLException ex) {
                throw ex;
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }


    private List<Card> toListCard(ResultSet resultSet) throws SQLException {
        List<Card> ret = new ArrayList<>();

        while(resultSet.next()) {
            Card card = new Card();
            card.setId(resultSet.getLong("id"));
            card.setCardNumber(resultSet.getInt("CardNumber"));
            card.setCardBalance(resultSet.getInt("CardBalance"));
            card.setAccountId(resultSet.getLong("AccountId"));
        }
        return ret;
    }
}

