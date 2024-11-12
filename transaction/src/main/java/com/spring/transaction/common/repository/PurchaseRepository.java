package com.spring.transaction.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.transaction.common.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
