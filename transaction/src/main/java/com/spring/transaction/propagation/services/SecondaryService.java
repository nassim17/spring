package com.spring.transaction.propagation.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.transaction.common.entity.Purchase;
import com.spring.transaction.common.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecondaryService {

    private final PurchaseRepository purchaseRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveWithRequired(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithRequiresNew(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void saveWithNested(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveWithSupports(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveWithNotSupported(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveWithMandatory(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void saveWithNever(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
