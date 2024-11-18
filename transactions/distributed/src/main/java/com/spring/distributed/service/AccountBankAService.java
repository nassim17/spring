package com.spring.distributed.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.banka.repository.AccountBankARepository;

@Service
public class AccountBankAService {

    private final AccountBankARepository accountBankARepository;

    public AccountBankAService(final AccountBankARepository accountBankARepository) {
        this.accountBankARepository = accountBankARepository;
    }

    @Transactional("transactionManagerBankA")
    public void debitFromBankA(Long fromAccountId, double amount) {
        AccountBankA accountA = accountBankARepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Account in Bank A not found"));

        if (accountA.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in Bank A");
        }

        accountA.setBalance(accountA.getBalance() - amount);
        accountBankARepository.save(accountA);
    }
}
