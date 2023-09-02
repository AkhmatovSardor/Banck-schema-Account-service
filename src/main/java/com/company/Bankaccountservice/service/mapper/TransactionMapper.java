package com.company.Bankaccountservice.service.mapper;

import com.company.Bankaccountservice.dto.TransactionDto;
import com.company.Bankaccountservice.entity.Transaction;
import com.company.Bankaccountservice.service.AccountService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = {Collectors.class})
public abstract class TransactionMapper {
    @Autowired
    protected AccountService accountService;
    @Mapping(target = "account",ignore = true)
    public abstract TransactionDto toDto(Transaction transaction);
    @Mapping(target = "account",expression = "java(accountService.getAccountByTransaction(transaction.getTransactionId()).getData())")
    public abstract TransactionDto toDtoWithAccount(Transaction transaction);
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract Transaction toEntity(TransactionDto dto);
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Transaction transaction, TransactionDto dto);
}
