package com.githib.nikel90.bankapi.service;

import com.githib.nikel90.bankapi.data.model.User;
import com.githib.nikel90.bankapi.data.repository.UserRepository;
import com.githib.nikel90.bankapi.data.transfer.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    UserRepository userRepository;


    public UserDto registration(User user) throws SQLException {
        return fromUserDto(userRepository.save(user));
    }

    public UserDto getUserById(User user) throws SQLException {
        return fromUserDto(userRepository.getById(user.getId()));
    }

    public List<UserDto> getAll() throws SQLException {
        List<User> listUser = userRepository.getAll();
        List<UserDto> listUserDto = new ArrayList<>();

        for (User user : listUser) {
            listUserDto.add(fromUserDto(user));
        }
        return listUserDto;
    }

    private UserDto fromUserDto(User user) {
        return new UserDto(user.getId(), user.getSurname(), user.getName(), user.getAge(), user.getLogin());

    }
}
