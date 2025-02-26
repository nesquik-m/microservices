package com.example.service.impl;

import com.example.dto.AccountDto;
import com.example.dto.AccountFilterDto;
import com.example.entity.Account;
import com.example.enums.ErrorCode;
import com.example.exception.AlreadyExistsException;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.AccountMapper;
import com.example.mapper.AccountUpdateFactory;
import com.example.repository.AccountRepository;
import com.example.repository.specification.AccountSpecification;
import com.example.security.SecurityUtils;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Value("${app.scheduled.cutoff-time}")
    private int cutoffTime;

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OBJECT_NOT_FOUND));
    }

    @Override
    public AccountDto getAccount() {
        Account account = getAccountById(SecurityUtils.getUUIDFromSecurityContext());
        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto getAccountDtoById(UUID accountId) {
        return accountMapper.accountToAccountDto(getAccountById(accountId));
    }

    @Override
    @Transactional
    public void createAccount(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountDto updateAccount(Account account) {
        Account updatedAccount = getAccountById(SecurityUtils.getUUIDFromSecurityContext());
        AccountUpdateFactory.updateFields(updatedAccount, account);
        return accountMapper.accountToAccountDto(accountRepository.save(updatedAccount));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAccounts(Pageable pageable) {
        List<Account> accounts = accountRepository.findAll(pageable).getContent();
        return accountMapper.accountListToAccountDtoList(accounts);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getAllAccountIds(Pageable pageable) {
        List<Account> accounts = accountRepository.findAll(pageable).getContent();
        return accounts.stream()
                .map(Account::getId)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAccountsByFilter(AccountFilterDto accountSearchDto, Pageable pageable) {
        List<Account> all = accountRepository
                .findAll(AccountSpecification.withFilter(accountSearchDto), pageable)
                .getContent();
        return accountMapper.accountListToAccountDtoList(all);
    }

    @Override
    public void markAccountAsOnline(UUID accountId) {
        accountRepository.markAccountAsOnline(accountId);
    }

    @Scheduled(cron = "${app.scheduled.interval-in-cron}")
    public void markAccountAsOffline() {
        try {
            accountRepository.updateOfflineStatus(LocalDateTime.now().minusMinutes(cutoffTime));
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("Error updating offline status: {}", e.getMessage(), e);
        }
    }

}