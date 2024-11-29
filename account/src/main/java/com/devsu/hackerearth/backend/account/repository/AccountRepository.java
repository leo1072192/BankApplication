package com.devsu.hackerearth.backend.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Método personalizado para encontrar la cuenta por clientId
    Optional<Account> findByClientId(Long clientId);
}
