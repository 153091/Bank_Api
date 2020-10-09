package com.github.nikel90.bankapi.service;

import com.github.nikel90.bankapi.data.model.Card;
import com.github.nikel90.bankapi.data.repository.CardRepository;
import com.github.nikel90.bankapi.data.transfer.CardAddDto;
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
        return CardDto.fromCard(cardRepository.save(card));
    }

    public CardDto getCardById(long id) throws SQLException {
        return CardDto.fromCard(cardRepository.getById(id));
    }

    public List<CardDto> getAll() throws SQLException {
        List<Card> listCard = cardRepository.getAll();
        List<CardDto> listCardDto = new ArrayList<>();

        for (Card card : listCard) {
            listCardDto.add(CardDto.fromCard(card));
        }
        return listCardDto;
    }

    public CardDto addBalance(CardAddDto cardAddDto) throws SQLException {
        return CardDto.fromCard(cardRepository.addBalance(cardAddDto.getCardId(), cardAddDto.getAmount()));
    }
}

