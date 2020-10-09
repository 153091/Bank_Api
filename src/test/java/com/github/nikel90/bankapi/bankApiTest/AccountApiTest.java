package com.github.nikel90.bankapi.bankApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.model.User;
import com.github.nikel90.bankapi.data.repository.AccountRepository;
import com.github.nikel90.bankapi.data.repository.UserRepository;
import com.github.nikel90.bankapi.data.transfer.AccountDto;
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
public class AccountApiTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws SQLException {
        userRepository.save(new User("Nikita", "Bazhenov", 24, "nikel-90", "12345"));
        accountRepository.save(new Account(123123, 1));
    }


    @Test
    void getAllAccount() throws Exception {
        List<Account> accounts = accountRepository.getAll();
        List<AccountDto> accountDto = new ArrayList<>();

        for (Account account: accounts) {
            accountDto.add(AccountDto.fromAccount(account));
        }

        String expected = mapper.writeValueAsString(accountDto);

        mockMvc.perform(getRequest("/account"))
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


