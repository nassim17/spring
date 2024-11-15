package com.spring.transactional.propagation.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.transactional.common.entity.Purchase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

    private final SecondaryService secondaryService;

    @Transactional
    public void performWithRequired(Purchase purchase) {
        secondaryService.saveWithRequired(purchase);
    }
    public void performWithoutTransactionWithRequired(Purchase purchase) {
        secondaryService.saveWithRequired(purchase);
    }

    @Transactional
    public void performWithRequiresNew(Purchase purchase) {
        secondaryService.saveWithRequiresNew(purchase);
    }
    public void performWithoutTransactionWithRequiresNew(Purchase purchase) {
        secondaryService.saveWithRequiresNew(purchase);
    }

    @Transactional
    public void performWithNested(Purchase purchase) {
        secondaryService.saveWithNested(purchase);
    }
    public void performWithoutTransactionWithNested(Purchase purchase) {
        secondaryService.saveWithNested(purchase);
    }

    @Transactional
    public void performWithSupports(Purchase purchase) {
        secondaryService.saveWithSupports(purchase);
    }
    public void performWithoutTransactionWithSupports(Purchase purchase) {
        secondaryService.saveWithSupports(purchase);
    }

    @Transactional
    public void performWithNotSupported(Purchase purchase) {
        secondaryService.saveWithNotSupported(purchase);
    }
    public void performWithoutTransactionWithNotSupported(Purchase purchase) {
        secondaryService.saveWithNotSupported(purchase);
    }

    @Transactional
    public void performWithMandatory(Purchase purchase) {
        secondaryService.saveWithMandatory(purchase);
    }
    public void performWithoutTransactionWithMandatory(Purchase purchase) {
        secondaryService.saveWithMandatory(purchase);
    }

    @Transactional
    public void performWithNever(Purchase purchase) {
        secondaryService.saveWithNever(purchase);
    }
    public void performWithoutTransactionWithNever(Purchase purchase) {
        secondaryService.saveWithNever(purchase);
    }
}
