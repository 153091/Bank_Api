package com.githib.nikel90.bankapi.service;

import com.githib.nikel90.bankapi.data.model.User;
import com.githib.nikel90.bankapi.data.repository.UserRepository;
import com.githib.nikel90.bankapi.data.transfer.UserDto;

import java.sql.SQLException;

public class UserService {
    UserRepository userRepository;


    public UserDto registration(User user) throws SQLException {
        return fromUserDto(userRepository.save(user));
    }

    public UserDto getUserById(User user) throws SQLException {
        return fromUserDto(userRepository.getById(user.getId()));
    }

    public UserDto getAll(UserDto userDto) throws SQLException {
        return fromUserDto(userRepository.getAll());
    }


    private UserDto fromUserDto(User user) {
        return new UserDto(user.getId(), user.getSurname(), user.getName(), user.getAge(), user.getLogin());

    }

//    private User fromUser(UserDto userDto) {
//        return new User(userDto.)
//    }
}
