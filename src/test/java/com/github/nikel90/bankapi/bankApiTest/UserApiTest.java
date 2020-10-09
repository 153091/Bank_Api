package com.github.nikel90.bankapi.bankApiTest;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.nikel90.bankapi.data.model.User;
import com.github.nikel90.bankapi.data.repository.UserRepository;
import com.github.nikel90.bankapi.data.transfer.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() throws SQLException {
        userRepository.save(new User("Nikita", "Bazhenov", 24, "nikel-90", "12345"));
    }


    @Test
    void getAllUsers() throws Exception {
        List<User> users = userRepository.getAll();
        List<UserDto> usersDto= new ArrayList<>();

        for (User user : users) {
            usersDto.add(UserDto.fromUser(user));
        }

        String expected = mapper.writeValueAsString(usersDto);

//        final String expected = mapper.writeValueAsString(users
//                .stream()
//                .map(UserDto::fromUser)
//                .collect(Collectors.toList()));
        mockMvc.perform(getRequest("/user"))
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
