package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(TransactionDto::new).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return new TransactionDto(transaction);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
      
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Calcular el balance actual basado en las transacciones
        double currentBalance = calculateAccountBalance(account.getId());

        // Verificar si hay saldo suficiente
        double newBalance = currentBalance + transactionDto.getAmount();
        if (newBalance < 0) {
            throw new RuntimeException("Saldo no disponible");
        }

        // Crear la nueva transacción
        Transaction transaction = new Transaction(transactionDto);
        transaction.setBalance(newBalance); 
        transactionRepository.save(transaction);

        // Actualizar el balance de la cuenta

        accountRepository.save(account);

        return new TransactionDto(transaction);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Obtener todas las transacciones dentro del rango de fechas para el cliente
    List<Transaction> transactions = transactionRepository.findTransactionsByClientIdAndDateBetween(
        clientId,
        dateTransactionStart, 
        dateTransactionEnd
);

// Obtener los detalles de la cuenta del cliente
Account account = accountRepository.findByClientId(clientId)
        .orElseThrow(() -> new RuntimeException("Account not found"));

// Mapear las transacciones a declaraciones bancarias
return transactions.stream()
        .map(transaction -> new BankStatementDto(
            transaction,
            account.getClientId().toString(),
            account.getNumber(),
            account.getType(),
            account.getInitialAmount(),
            account.isActive()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        Transaction transaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId);
        return new TransactionDto(transaction);
    }

    // Método para calcular el balance actual de una cuenta
    private double calculateAccountBalance(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        double balance = 0.0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount(); // Sumar el monto de cada transacción
        }
        return balance;
    }

}
