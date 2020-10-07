package com.githib.nikel90.bankapi.service;

import com.githib.nikel90.bankapi.data.model.Card;
import com.githib.nikel90.bankapi.data.repository.CardRepository;
import com.githib.nikel90.bankapi.data.transfer.CardDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardService {
    CardRepository cardRepository;

    public CardDto register(Card card) throws SQLException {
        return fromCardDto(cardRepository.save(card));
    }

    public CardDto getCardById(long id) throws SQLException {
        return fromCardDto(cardRepository.getById(id));
    }

    public List<CardDto> getAll() throws SQLException {
        List<Card> listCard = cardRepository.getAll();
        List<CardDto> listCardDto = new ArrayList<>();

        for (Card card : listCard) {
            listCardDto.add(fromCardDto(card));
        }
        return listCardDto;
    }

    private CardDto fromCardDto(Card card) {
        return new CardDto(card.getId(), card.getCardNumber(), card.getCardBalance(), card.getAccountId());
    }
}

