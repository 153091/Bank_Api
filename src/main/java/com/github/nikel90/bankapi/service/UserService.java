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
        return fromUserDto(userRepository.save(user));
    }

    public UserDto getUserById(long id) throws SQLException {
        return fromUserDto(userRepository.getById(id));
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
