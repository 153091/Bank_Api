package com.github.nikel90.bankapi.data.repository;

import com.github.nikel90.bankapi.data.model.Card;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CardRepository implements CrudRepository<Card, Long> {
    private static final String GET_CARD_BY_ID = "SELECT * FROM CARD WHERE ID = ?;";
    private static final String GET_ALL_CARD = "SELECT * FROM CARD;";
    private static final String SAVE_CARD = "INSERT INTO CARD (CARD_NUMBER, CARD_BALANCE, ACCOUNT_ID) VALUES (?, ?, ?);";
    private final static String UPDATE_CARD = "UPDATE CARD SET CARD_NUMBER = ?, CARD_BALANCE = ?, ACCOUNT_ID = ?";
    private final static String DELETE_CARD = "DELETE FROM CARD WHERE ID = ?;";

    private final DataSource dataSource;

    public CardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Card getById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Card> card = toListCard(resultSet);
                if (!card.isEmpty()) {
                    return card.get(0);
                } else {
                    throw new SQLException("Card not found.");
                }
            }
        }
    }

    @Override
    public List<Card> getAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CARD)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                return toListCard(resultSet);
            }
        }
    }

    @Override
    public boolean removeById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CARD)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        }
    }

    @Override
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

    @Override
    public void update(Card card) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARD)) {

            statement.setInt(1, card.getCardNumber());
            statement.setInt(2, card.getCardBalance());
            statement.setLong(3, card.getAccountId());

            statement.execute();
        }
    }


    private List<Card> toListCard(ResultSet resultSet) throws SQLException {
        List<Card> ret = new ArrayList<>();

        while(resultSet.next()) {
            Card card = new Card();
            card.setId(resultSet.getLong("id"));
            card.setCardNumber(resultSet.getInt("card_number"));
            card.setCardBalance(resultSet.getInt("card_balance"));
            card.setAccountId(resultSet.getLong("account_id"));

            ret.add(card);
        }
        return ret;
    }

}

