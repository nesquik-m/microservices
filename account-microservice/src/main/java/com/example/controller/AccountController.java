package com.example.controller;

import com.example.annotation.LogAspect;
import com.example.aop.LogType;
import com.example.dto.AccountDto;
import com.example.dto.AccountFilterDto;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ROLE_USER')")
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping("/me")
    @LogAspect(type = LogType.CONTROLLER)
    public AccountDto getAccount() {
        return accountService.getAccount();
    }

    @GetMapping("/{id}")
    @LogAspect(type = LogType.CONTROLLER)
    public AccountDto getAccountById(@PathVariable("id") UUID accountId) {
        return accountService.getAccountDtoById(accountId);
    }

    @PutMapping("/me")
    @LogAspect(type = LogType.CONTROLLER)
    public AccountDto updateAccount(@RequestBody AccountDto accountDto) {
        return accountService.updateAccount(accountMapper.accountDtoToAccount(accountDto));
    }

    @GetMapping("/accounts")
    @LogAspect(type = LogType.CONTROLLER)
    public List<AccountDto> getAllAccount(@RequestParam(defaultValue = "0") int offset,
                                       @RequestParam(defaultValue = "25") int limit) {
        return accountService.getAccounts(PageRequest.of(offset, limit));
    }

    @GetMapping("/ids")
    @LogAspect(type = LogType.CONTROLLER)
    public List<UUID> getAllAccountIds(@RequestParam(defaultValue = "0") int offset,
                                       @RequestParam(defaultValue = "25") int limit) {
        return accountService.getAllAccountIds(PageRequest.of(offset, limit));
    }

    @GetMapping("/filter")
    @LogAspect(type = LogType.CONTROLLER)
    public List<AccountDto> getAccountsByFilter(@ModelAttribute AccountFilterDto accountFilterDto,
                                                @RequestParam(defaultValue = "0") int offset,
                                                @RequestParam(defaultValue = "25") int limit) {
        return accountService.getAccountsByFilter(accountFilterDto, PageRequest.of(offset, limit));
    }

}