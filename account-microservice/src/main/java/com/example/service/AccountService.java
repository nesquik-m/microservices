package com.example.service;

import com.example.dto.AccountDto;
import com.example.dto.AccountFilterDto;
import com.example.entity.Account;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDto getAccount();

    Account getAccountById(UUID accountId);

    AccountDto getAccountDtoById(UUID accountId);

    void createAccount(Account account);

    AccountDto updateAccount(Account account);

    List<AccountDto> getAccounts(Pageable pageable);

    List<UUID> getAllAccountIds(Pageable pageable);

    List<AccountDto> getAccountsByFilter(AccountFilterDto accountFilterDto, Pageable pageable);

    void markAccountAsOnline(UUID accountId);

}