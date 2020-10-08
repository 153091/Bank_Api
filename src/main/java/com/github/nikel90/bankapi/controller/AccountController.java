package com.github.nikel90.bankapi.controller;

import com.github.nikel90.bankapi.data.transfer.AccountDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AccountController {
    private final AccountDto accountDto;

    public AccountController(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    @PostMapping("/account/register")
    public String register(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/account")
    public String getAllAccount(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/account/")
    public String getUserByAccount(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


}
