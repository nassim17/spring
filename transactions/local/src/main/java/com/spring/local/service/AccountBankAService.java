package com.spring.local.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.banka.repository.AccountBankARepository;

@Service
public class AccountBankAService {

    private final AccountBankARepository accountBankARepository;
    private final JmsTemplate xaJmsTemplate;
    private final JmsTemplate jmsTemplate;

    public AccountBankAService(final AccountBankARepository accountBankARepository,
            final JmsTemplate xaJmsTemplate,
            final JmsTemplate jmsTemplate) {
        this.accountBankARepository = accountBankARepository;
        this.xaJmsTemplate = xaJmsTemplate;
        this.jmsTemplate = jmsTemplate;
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

    /**
     * Créer ou mettre à jour un AccountBankA et envoyer un message à ActiveMQ.
     * @param accountBankA Le compte à créer ou à mettre à jour.
     */
    @Transactional(value = "transactionManagerBankA", propagation = Propagation.REQUIRES_NEW)
    public void localCreateOrUpdateAccountBank(AccountBankA accountBankA) {

        accountBankARepository.save(accountBankA);

        // Étape 2 : Envoyer un message à ActiveMQ concernant la mise à jour
        String message = "Account updated: " + accountBankA.getName() + ", Balance: " + accountBankA.getBalance();
        jmsTemplate.convertAndSend("accountBankQueue", message);

        // Étape 3 : Simuler une erreur potentielle
        if (accountBankA.getBalance() < 0) {
            throw new RuntimeException("Balance cannot be negative!");
        }
    }
}
