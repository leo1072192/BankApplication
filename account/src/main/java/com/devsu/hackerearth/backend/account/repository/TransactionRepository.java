package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        // Buscar transacciones por ID de cuenta
        List<Transaction> findByAccountId(Long accountId);

        // Buscar transacciones por clientId (a través de Account)
        List<Transaction> findByClientId(Long clientId);

        // Buscar transacciones por cliente y rango de fechas
        List<Transaction> findTransactionsByClientIdAndDateBetween(
                        @Param("clientId") Long clientId,
                        @Param("dateTransactionStart") Date dateTransactionStart,
                        @Param("dateTransactionEnd") Date dateTransactionEnd);

        // Obtener la última transacción por cuenta, ordenada por fecha descendente
        Transaction findTopByAccountIdOrderByDateDesc(Long accountId);
}
