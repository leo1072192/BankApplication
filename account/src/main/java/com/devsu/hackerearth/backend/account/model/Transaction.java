package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction extends Base {

	private Date date;
	private String type;
	private double amount;
	private double balance;

	@Column(name = "account_id")
	private Long accountId;

	// Campo adicional para almacenar clientId directamente en Transaction
	@Column(name = "client_id")
	private Long clientId;

	// Constructor que recibe un TransactionDto
	public Transaction(TransactionDto transactionDto) {
		this.date = transactionDto.getDate();
		this.type = transactionDto.getType();
		this.amount = transactionDto.getAmount();
		this.balance = transactionDto.getBalance();
		this.accountId = transactionDto.getAccountId();
		this.clientId = transactionDto.getClienteId();
	}
	// Método de conveniencia para obtener el clientId

}
