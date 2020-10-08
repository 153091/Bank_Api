package com.github.nikel90.bankapi.service;

import com.github.nikel90.bankapi.data.model.Card;
import com.github.nikel90.bankapi.data.repository.CardRepository;
import com.github.nikel90.bankapi.data.transfer.CardDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

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

