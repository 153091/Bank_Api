package com.githib.nikel90.bankapi.service;

import com.githib.nikel90.bankapi.data.model.Account;
import com.githib.nikel90.bankapi.data.repository.AccountRepository;
import com.githib.nikel90.bankapi.data.transfer.AccountDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountRepository accountRepository;

    public AccountDto registration(Account account) throws SQLException {
        return fromAccountDto(accountRepository.save(account));
    }

    public AccountDto getAccountById(Account account) throws SQLException {
        return fromAccountDto(accountRepository.getById(account.getId()));
    }

    public List<AccountDto> getAll() throws SQLException {
        List<Account> listAccount = accountRepository.getAll();
        List<AccountDto> listAccountDto = new ArrayList<>();

        for (Account account : listAccount) {
            listAccountDto.add(fromAccountDto(account));
        }
        return listAccountDto;
    }

    private AccountDto fromAccountDto(Account account) {
        return new AccountDto(account.getId(), account.getAccountNumber(), account.getUserId());
    }
}
