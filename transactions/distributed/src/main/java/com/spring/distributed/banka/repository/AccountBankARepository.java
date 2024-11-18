package com.spring.distributed.banka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.distributed.banka.entity.AccountBankA;

public interface AccountBankARepository extends JpaRepository<AccountBankA, Long> {
}
