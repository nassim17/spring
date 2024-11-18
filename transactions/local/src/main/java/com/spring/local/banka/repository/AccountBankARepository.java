package com.spring.local.banka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.distributed.banka.entity.AccountBankA;

public interface AccountBankARepository extends JpaRepository<AccountBankA, Long> {
}
