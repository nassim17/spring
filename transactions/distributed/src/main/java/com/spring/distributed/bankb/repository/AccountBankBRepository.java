package com.spring.distributed.bankb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.distributed.bankb.entity.AccountBankB;

public interface AccountBankBRepository extends JpaRepository<AccountBankB, Long> {
}
