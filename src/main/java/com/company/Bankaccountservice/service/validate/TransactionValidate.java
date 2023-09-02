package com.company.Bankaccountservice.service.validate;

import com.company.Bankaccountservice.dto.ErrorDto;
import com.company.Bankaccountservice.dto.TransactionDto;
import com.company.Bankaccountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionValidate {
    private final AccountService accountService;
    public List<ErrorDto> validate(TransactionDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
        return errors;
    }
}
