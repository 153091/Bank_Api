package com.github.nikel90.bankapi.service;

import com.github.nikel90.bankapi.data.model.User;
import com.github.nikel90.bankapi.data.repository.UserRepository;
import com.github.nikel90.bankapi.data.transfer.UserDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto register(User user) throws SQLException {
        return UserDto.fromUser(userRepository.save(user));
    }

    public UserDto getUserById(long id) throws SQLException {
        return UserDto.fromUser(userRepository.getById(id));
    }

    public List<UserDto> getAll() throws SQLException {
        List<User> listUser = userRepository.getAll();
        List<UserDto> listUserDto = new ArrayList<>();

        for (User user : listUser) {
            listUserDto.add(UserDto.fromUser(user));
        }
        return listUserDto;
    }

}
