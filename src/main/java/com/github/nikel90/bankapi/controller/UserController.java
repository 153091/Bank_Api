package com.github.nikel90.bankapi.controller;

import com.github.nikel90.bankapi.data.model.User;
import com.github.nikel90.bankapi.data.transfer.UserDto;
import com.github.nikel90.bankapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public UserDto register(@RequestBody User user) throws SQLException {
        return userService.register(user);
    }

    @GetMapping("/user")
    public List<UserDto> getAllUsers() throws SQLException {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) throws SQLException {
        return userService.getUserById(id);
    }
}
