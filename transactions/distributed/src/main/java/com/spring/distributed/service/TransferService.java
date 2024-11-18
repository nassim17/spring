package com.spring.distributed.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.bankb.entity.AccountBankB;
import com.spring.distributed.banka.repository.AccountBankARepository;
import com.spring.distributed.bankb.repository.AccountBankBRepository;

@Service
public class TransferService {

    private final AccountBankARepository accountBankARepository;
    private final AccountBankBRepository accountBankBRepository;
    private final AccountBankAService accountBankAService;
    private final AccountBankBService accountBankBService;

    public TransferService(AccountBankARepository accountBankARepository, AccountBankBRepository accountBankBRepository,
            AccountBankAService accountBankAService, AccountBankBService accountBankBService) {
        this.accountBankARepository = accountBankARepository;
        this.accountBankBRepository = accountBankBRepository;
        this.accountBankAService = accountBankAService;
        this.accountBankBService = accountBankBService;
    }

    @Transactional("transactionManagerJta")
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        // Débit du compte dans Bank A
        AccountBankA accountA = accountBankARepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Account in Bank A not found"));
        if (accountA.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in Bank A");
        }
        accountA.setBalance(accountA.getBalance() - amount);
        accountBankARepository.save(accountA);

        // Crédit du compte dans Bank B
        AccountBankB accountB = accountBankBRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Account in Bank B not found"));
        accountB.setBalance(accountB.getBalance() + amount);
        accountBankBRepository.save(accountB);
    }

    public void localTransfer(Long fromAccountId, Long toAccountId, double amount) {
        accountBankAService.debitFromBankA(fromAccountId, amount);  // Transaction pour BankA
        accountBankBService.creditToBankB(toAccountId, amount);    // Transaction pour BankB
    }
}
