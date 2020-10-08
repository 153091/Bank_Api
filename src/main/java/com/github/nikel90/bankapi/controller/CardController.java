package com.github.nikel90.bankapi.controller;

import com.github.nikel90.bankapi.data.model.Card;
import com.github.nikel90.bankapi.data.transfer.CardAddDto;
import com.github.nikel90.bankapi.data.transfer.CardDto;
import com.github.nikel90.bankapi.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card/register")
    public CardDto register(@RequestBody Card card) throws SQLException {
        return cardService.register(card);
    }

    @GetMapping("/card")
    public List<CardDto> getAllCard() throws SQLException {
        return cardService.getAll();
    }

    @GetMapping("/card/{id}")
    public CardDto getCardById(@PathVariable("id") Long id) throws SQLException {
        return cardService.getCardById(id);
    }

    @GetMapping("/card/balance/{id}")
    public CardDto getBalance(@PathVariable("id") Long id) throws SQLException {
        return cardService.getCardById(id);
    }

    @PostMapping("/card/balance/add")
    public CardDto addBalance(@RequestBody CardAddDto cardAddDto) throws SQLException {
        return cardService.addBalance(cardAddDto);
    }
}