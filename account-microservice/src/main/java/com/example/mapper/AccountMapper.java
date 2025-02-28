package com.example.mapper;

import com.example.dto.AccountDto;
import com.example.dto.kafka.NewUserEvent;
import com.example.entity.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccountMapper {

    public Account newUserEventToAccount(NewUserEvent newUserEvent) {
        if (newUserEvent == null) {
            return null;
        }
        
        return Account.builder()
                .id(newUserEvent.getId())
                .email(newUserEvent.getEmail())
                .firstName(newUserEvent.getFirstName())
                .lastName(newUserEvent.getLastName())
                .isOnline(true)
                .lastOnlineTime(LocalDateTime.now())
                .build();
    }


    public AccountDto accountToAccountDto(Account account) {
        if (account == null) {
            return null;
        }

        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .phone(account.getPhone())
                .city(account.getCity())
                .country(account.getCountry())
                .birthDate(account.getBirthDate())
                .about(account.getAbout())
                .createdOn(account.getCreatedOn())
                .updatedOn(account.getUpdatedOn())
                .build();
    }


    public Account accountDtoToAccount(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        return Account.builder()
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .phone(accountDto.getPhone())
                .city(accountDto.getCity())
                .country(accountDto.getCountry())
                .birthDate(accountDto.getBirthDate())
                .about(accountDto.getAbout())
                .id(accountDto.getId())
                .email(accountDto.getEmail())
                .build();
    }

    public List<AccountDto> accountListToAccountDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(this::accountToAccountDto)
                .toList();
    }

}