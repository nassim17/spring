package com.spring.transactional.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.transactional.common.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
