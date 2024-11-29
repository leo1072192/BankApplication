package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        // Obtener todas las cuentas y mapearlas a DTOs utilizando el constructor de
        // AccountDto
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDto( // Usamos el constructor de AccountDto directamente
                        account.getId(),
                        account.getNumber(),
                        account.getType(),
                        account.getInitialAmount(),
                        account.isActive(),
                        account.getClientId()))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // Obtener la cuenta por ID y mapearla a DTO
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Convertir el DTO a la entidad, guardar y luego mapear de vuelta a DTO
        Account account = new Account();
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        account = accountRepository.save(account);
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Convertir DTO a entidad, actualizar y luego mapear de vuelta
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        account = accountRepository.save(account);
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Actualización parcial
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (partialAccountDto.getNumber() != null) {
            account.setNumber(partialAccountDto.getNumber());
        }
        if (partialAccountDto.getType() != null) {
            account.setType(partialAccountDto.getType());
        }
        if (partialAccountDto.getInitialAmount() != null) {
            account.setInitialAmount(partialAccountDto.getInitialAmount());
        }
        if (partialAccountDto.getIsActive() != null) {
            account.setActive(partialAccountDto.getIsActive());
        }

        // Guardar la cuenta actualizada
        account = accountRepository.save(account);

        // Devolver la cuenta actualizada como DTO
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

}
