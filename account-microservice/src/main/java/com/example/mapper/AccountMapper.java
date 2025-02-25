package com.example.mapper;

import com.example.dto.AccountDto;
import com.example.dto.kafka.NewUserEvent;
import com.example.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    Account newUserEventToAccount(NewUserEvent newUserEvent);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "about", target = "about")
    @Mapping(source = "createdOn", target = "createdOn")
    @Mapping(source = "updatedOn", target = "updatedOn")
    AccountDto accountToAccountDto(Account account);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "about", target = "about")
    Account accountDtoToAccount(AccountDto accountDto);

    List<AccountDto> accountListToAccountDtoList(List<Account> accounts);

}