package com.github.nikel90.bankapi.controller;

import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.transfer.AccountDto;
import com.github.nikel90.bankapi.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/register")
    public AccountDto register(@RequestBody Account account) throws SQLException {
        return accountService.register(account);
    }

    @GetMapping("/account")
    public List<AccountDto> getAllUsers() throws SQLException {
        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public AccountDto getUserById(@PathVariable("id") Long id) throws SQLException {
        return accountService.getAccountById(id);
    }
}
