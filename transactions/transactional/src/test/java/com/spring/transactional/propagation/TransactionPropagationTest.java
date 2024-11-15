package com.spring.transactional.propagation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.NestedTransactionNotSupportedException;

import com.spring.transactional.common.entity.Purchase;
import com.spring.transactional.common.repository.PurchaseRepository;
import com.spring.transactional.propagation.services.MainService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionPropagationTest {

    @Autowired
    private MainService mainService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp() {
        purchaseRepository.deleteAll();
    }

    // REQUIRED avec et sans transaction dans MainService
    @Test
    @Order(1)
    void testRequiredPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with REQUIRED and transaction in MainService");
        mainService.performWithRequired(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved with REQUIRED propagation and active transaction");
    }

    @Test
    @Order(2)
    void testRequiredPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with REQUIRED and no transaction in MainService");
        mainService.performWithoutTransactionWithRequired(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved as REQUIRED starts a new transaction when none exists");
    }

    // REQUIRES_NEW avec et sans transaction dans MainService
    @Test
    @Order(3)
    void testRequiresNewPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with REQUIRES_NEW and transaction in MainService");
        mainService.performWithRequiresNew(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved with REQUIRES_NEW propagation");
    }

    @Test
    @Order(4)
    void testRequiresNewPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with REQUIRES_NEW and no transaction in MainService");
        mainService.performWithoutTransactionWithRequiresNew(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved as REQUIRES_NEW starts a new transaction");
    }

    // NESTED avec et sans transaction dans MainService
    @Test
    @Order(5)
    void testNestedPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with NESTED and transaction in MainService");
        try {
            mainService.performWithNested(purchase);
            fail("Expected NestedTransactionNotSupportedException was not thrown");
        } catch (NestedTransactionNotSupportedException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        assertEquals(0, purchaseRepository.count(), "Purchase should not be saved due to transaction failure");
    }

    @Test
    @Order(6)
    void testNestedPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with NESTED and no transaction in MainService");
        mainService.performWithoutTransactionWithNested(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved as a new transaction was started");
    }

    // SUPPORTS avec et sans transaction dans MainService
    @Test
    @Order(7)
    void testSupportsPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with SUPPORTS and transaction in MainService");
        mainService.performWithSupports(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved with SUPPORTS and active transaction");
    }

    @Test
    @Order(8)
    void testSupportsPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with SUPPORTS and no transaction in MainService");
        mainService.performWithoutTransactionWithSupports(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved, even without an active transaction");
    }

    // NOT_SUPPORTED avec et sans transaction dans MainService
    @Test
    @Order(9)
    void testNotSupportedPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with NOT_SUPPORTED and transaction in MainService");
        mainService.performWithNotSupported(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved, even though the transaction is suspended");
    }

    @Test
    @Order(10)
    void testNotSupportedPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with NOT_SUPPORTED and no transaction in MainService");
        mainService.performWithoutTransactionWithNotSupported(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved, even without an active transaction");
    }

    // MANDATORY avec et sans transaction dans MainService
    @Test
    @Order(11)
    void testMandatoryPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with MANDATORY and transaction in MainService");
        mainService.performWithMandatory(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved with MANDATORY and active transaction");
    }

    @Test
    @Order(12)
    void testMandatoryPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with MANDATORY and no transaction in MainService");
        assertThrows(IllegalTransactionStateException.class, () -> mainService.performWithoutTransactionWithMandatory(purchase),
                "Expected IllegalTransactionStateException was not thrown");
    }

    // NEVER avec et sans transaction dans MainService
    @Test
    @Order(13)
    void testNeverPropagationWithTransaction() {
        Purchase purchase = new Purchase("Purchase with NEVER and transaction in MainService");
        assertThrows(IllegalTransactionStateException.class, () -> mainService.performWithNever(purchase),
                "Expected IllegalTransactionStateException was not thrown");
    }

    @Test
    @Order(14)
    void testNeverPropagationWithoutTransaction() {
        Purchase purchase = new Purchase("Purchase with NEVER and no transaction in MainService");
        mainService.performWithoutTransactionWithNever(purchase);
        assertEquals(1, purchaseRepository.count(), "Purchase should be saved as NEVER works without transaction");
    }
}
