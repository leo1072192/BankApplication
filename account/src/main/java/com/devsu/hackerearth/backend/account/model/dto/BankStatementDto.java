package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import com.devsu.hackerearth.backend.account.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankStatementDto {
    
	private Date date;
	private String client;
	private String accountNumber;
	private String accountType;
	private double initialAmount;
    private boolean isActive;
	private String transactionType;
	private double amount;
	private double balance;
	 // Constructor que acepta un objeto Transaction
	 public BankStatementDto(Transaction transaction, String client, String accountNumber, String accountType, double initialAmount, boolean isActive) {
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.balance = transaction.getBalance();
        this.transactionType = transaction.getType();
    
        this.client = client;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.initialAmount = initialAmount;
        this.isActive = isActive;
    }
}

