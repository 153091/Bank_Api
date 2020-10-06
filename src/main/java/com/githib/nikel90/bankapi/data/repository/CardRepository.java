package com.githib.nikel90.bankapi.data.repository;

import com.githib.nikel90.bankapi.data.model.Card;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private static final String GET_CARD_BY_ID = "SELECT * FROM CARD WHERE ID = ?";
    private static final String GET_ALL_CARD = "SELECT * FROM CARD";
    private static final String SAVE_CARD = "INSERT INTO CARD (CARD_NUBMER, CARD_BAlANCE, ACCOUNT_ID) VALUES (?, ?, ?);";

    private final DataSource dataSource;

    public CardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Card getById(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Card> card = toListCard(resultSet);
                if (card.isEmpty()) {
                    return card.get(0);
                } else {
                    throw new SQLException("Card not found.");
                }
            }
        }
    }


    public List<Card> getAll(Card card) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CARD)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                return toListCard(resultSet);
            }
        }
    }


    public Card save(Card card) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_CARD,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, card.getCardNumber());
            statement.setInt(2, card.getCardBalance());
            statement.setLong(3, card.getAccountId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getLong(1));
                    return card;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
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

