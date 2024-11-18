package com.spring.distributed.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.banka.repository.AccountBankARepository;

@Service
public class AccountBankAService {

    private final AccountBankARepository accountBankARepository;
    private final JmsTemplate xaJmsTemplate;

    public AccountBankAService(final AccountBankARepository accountBankARepository,
            final JmsTemplate xaJmsTemplate) {
        this.accountBankARepository = accountBankARepository;
        this.xaJmsTemplate = xaJmsTemplate;
    }


    /**
     * Créer ou mettre à jour un AccountBankA et envoyer un message à ActiveMQ.
     * @param accountBankA Le compte à créer ou à mettre à jour.
     */
    @Transactional("transactionManagerJta")
    public void createOrUpdateAccountBank(AccountBankA accountBankA) {

        accountBankARepository.save(accountBankA);

        // Étape 2 : Envoyer un message à ActiveMQ concernant la mise à jour
        String message = "Account updated: " + accountBankA.getName() + ", Balance: " + accountBankA.getBalance();
        xaJmsTemplate.convertAndSend("accountBankQueue", message);

        // Étape 3 : Simuler une erreur potentielle
        if (accountBankA.getBalance() < 0) {
            throw new RuntimeException("Balance cannot be negative!");
        }
    }
}
