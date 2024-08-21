package com.example.demo.infrastructure.repository;

import com.example.demo.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Account entity.
 **/
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountNumber(String accountNumber);
}
