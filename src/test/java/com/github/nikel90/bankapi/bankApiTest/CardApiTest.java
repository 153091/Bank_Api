package com.github.nikel90.bankapi.bankApiTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.model.Card;
import com.github.nikel90.bankapi.data.model.User;
import com.github.nikel90.bankapi.data.repository.AccountRepository;
import com.github.nikel90.bankapi.data.repository.CardRepository;
import com.github.nikel90.bankapi.data.repository.UserRepository;
import com.github.nikel90.bankapi.data.transfer.AccountDto;
import com.github.nikel90.bankapi.data.transfer.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardApiTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws SQLException {
        userRepository.save(new User("Nikita", "Bazhenov", 24, "nikel-90", "12345"));
        accountRepository.save(new Account(123123, 1));
        cardRepository.save(new Card(40181232, 1222, 1));
    }


    @Test
    void getAllAccount() throws Exception {
        List<Card> cards = cardRepository.getAll();
        List<CardDto> cardDto = new ArrayList<>();

        for (Card card: cards) {
            cardDto.add(CardDto.fromCard(card));
        }

        String expected = mapper.writeValueAsString(cardDto);

//        final String expected = mapper.writeValueAsString(users
//                .stream()
//                .map(UserDto::fromUser)
//                .collect(Collectors.toList()));
        mockMvc.perform(getRequest("/card"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    private MockHttpServletRequestBuilder getRequest(String url) {
        return MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

}

