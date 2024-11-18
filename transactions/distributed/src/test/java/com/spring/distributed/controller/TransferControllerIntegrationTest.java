package com.spring.distributed.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.distributed.banka.entity.AccountBankA;
import com.spring.distributed.banka.repository.AccountBankARepository;
import com.spring.distributed.bankb.entity.AccountBankB;
import com.spring.distributed.bankb.repository.AccountBankBRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TransferControllerIntegrationTest {

    private AccountBankA accountA;

    private AccountBankB accountB;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountBankARepository accountBankARepository;

    @Autowired
    private AccountBankBRepository accountBankBRepository;

    @BeforeEach
    void setUp() {
        // Initialisation des données pour BankA
        accountA = new AccountBankA();
        accountA.setName("AccountA");
        accountA.setBalance(1000.0);
        accountBankARepository.save(accountA);

        // Initialisation des données pour BankB
        accountB = new AccountBankB();
        accountB.setName("AccountB");
        accountB.setBalance(500.0);
        accountBankBRepository.save(accountB);
    }

    @AfterEach
    void tearDown() {
        // Nettoyer les données après chaque test
        accountBankARepository.delete(accountA);
        accountBankBRepository.delete(accountB);
    }

    @Test
    void transferFundsSuccessfulTransferTest() throws Exception {
        // Effectuer un transfert de 200.0
        mockMvc.perform(post("/api/transfer")
                        .param("fromAccountId", accountA.getId().toString())
                        .param("toAccountId", accountB.getId().toString())
                        .param("amount", "200.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful"));

        // Vérifier les soldes après transfert
        accountA = accountBankARepository.findById(accountA.getId()).orElseThrow();
        accountB = accountBankBRepository.findById(accountB.getId()).orElseThrow();

        assertEquals(800.0, accountA.getBalance());
        assertEquals(700.0, accountB.getBalance());
    }

    @Test
    void transferFundsInsufficientFundsRollbackTest() throws Exception {
        // Effectuer un transfert de 2000.0 (solde insuffisant)
        mockMvc.perform(post("/api/transfer")
                        .param("fromAccountId", accountA.getId().toString())
                        .param("toAccountId", accountB.getId().toString())
                        .param("amount", "2000.0"))
                .andExpect(status().is5xxServerError());

        // Vérifier que les soldes sont inchangés
        accountA = accountBankARepository.findById(accountA.getId()).orElseThrow();
        accountB = accountBankBRepository.findById(accountB.getId()).orElseThrow();

        assertEquals(1000.0, accountA.getBalance()); // Pas de débit
        assertEquals(500.0, accountB.getBalance());  // Pas de crédit
    }

    @Test
    void rollbackWhenSecondOperationFailsTest() throws Exception {
        // Injecter une exception pour l'opération sur Bank B
        accountBankBRepository.deleteById(accountB.getId());

        // Exécuter la requête de transfert
        mockMvc.perform(post("/api/transfer")
                        .param("fromAccountId", accountA.getId().toString())
                        .param("toAccountId", accountB.getId().toString())
                        .param("amount", "100.0"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Account in Bank B not found")));

        // Vérifier que le solde du compte dans Bank A n'a pas été modifié
        accountA = accountBankARepository.findById(accountA.getId()).orElseThrow();
        assertEquals(1000.0, accountA.getBalance());

        // Vérifier que le compte dans Bank B n'a pas été crédité (car rollback)
        assertFalse(accountBankBRepository.existsById(accountB.getId()));
    }

    @Test
    void localTransferWhenSecondTransactionFailsTest() throws Exception {
        // Injecter une exception pour l'opération sur Bank B
        accountBankBRepository.deleteById(accountB.getId());

        // Simuler une requête REST pour effectuer un transfert
        mockMvc.perform(post("/api/local-transfer")
                        .param("fromAccountId", accountA.getId().toString())
                        .param("toAccountId", accountB.getId().toString())
                        .param("amount", "500"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(Matchers.containsString("Transfer failed")));

        // Vérifier que le solde du compte dans Bank A a été modifié
        accountA = accountBankARepository.findById(accountA.getId()).orElseThrow();

        // Vérifier que le compte dans Bank B n'a pas été crédité
        assertFalse(accountBankBRepository.existsById(accountB.getId()));

        Assertions.assertEquals(500.0, accountA.getBalance(), "Bank A should not rollback");
    }
}