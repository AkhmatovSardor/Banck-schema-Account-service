package com.company.Bankaccountservice.service.mapper;

import com.company.Bankaccountservice.client.service.CreditCardClient;
import com.company.Bankaccountservice.dto.AccountDto;
import com.company.Bankaccountservice.entity.Account;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Collectors.class})
public abstract class AccountMapper {
    @Autowired
    protected CreditCardClient creditCardClient;

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Account toEntity(AccountDto dto);
    @Mapping(target = "creditCard",ignore = true)
    public abstract AccountDto toDto(Account account);

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Account account, AccountDto dto);

    @Mapping(target = "creditCard", expression = "java(creditCardClient.getCreditCardsByAccountId(account.getAccountId()).getData())")
    public abstract AccountDto toDtoWithCreditCard(Account account);
}