package com.spring.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.distributed.bankb.entity.AccountBankB;
import com.spring.distributed.bankb.repository.AccountBankBRepository;

@Service
public class AccountBankBService {

    private final AccountBankBRepository accountBankBRepository;

    public AccountBankBService(final AccountBankBRepository accountBankBRepository) {
        this.accountBankBRepository = accountBankBRepository;
    }

    @Transactional("transactionManagerBankB")
    public void creditToBankB(Long toAccountId, double amount) {
        AccountBankB accountB = accountBankBRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Account in Bank B not found"));

        accountB.setBalance(accountB.getBalance() + amount);
        accountBankBRepository.save(accountB);
    }
}
