package com.spring.distributed.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.service.AccountBankAService;

@RestController
@RequestMapping("/api")
public class AccountBankController {

    private final AccountBankAService accountBankAService;

    public AccountBankController(final AccountBankAService accountBankAService) {
        this.accountBankAService = accountBankAService;
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@RequestBody AccountBankA accountBankA) {
        try {
            accountBankAService.createOrUpdateAccountBank(accountBankA);
            return "Account updated and message sent to ActiveMQ";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
