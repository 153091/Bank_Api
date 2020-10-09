package com.github.nikel90.bankapi.service;

import com.github.nikel90.bankapi.data.model.Account;
import com.github.nikel90.bankapi.data.repository.AccountRepository;
import com.github.nikel90.bankapi.data.transfer.AccountDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto register(Account account) throws SQLException {
        return AccountDto.fromAccount(accountRepository.save(account));
    }

    public AccountDto getAccountById(long id) throws SQLException {
        return AccountDto.fromAccount(accountRepository.getById(id));
    }

    public List<AccountDto> getAll() throws SQLException {
        List<Account> listAccount = accountRepository.getAll();
        List<AccountDto> listAccountDto = new ArrayList<>();

        for (Account account : listAccount) {
            listAccountDto.add(AccountDto.fromAccount(account));
        }
        return listAccountDto;
    }
}
