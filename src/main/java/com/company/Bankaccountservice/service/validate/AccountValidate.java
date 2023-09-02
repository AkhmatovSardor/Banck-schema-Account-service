package com.company.Bankaccountservice.service.validate;

import com.company.Bankaccountservice.dto.AccountDto;
import com.company.Bankaccountservice.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidate {
   // private final BranchService branchService;
    public List<ErrorDto> validate(AccountDto dto) {
        List<ErrorDto> errors=new ArrayList<>();
      //  if (branchService.get(dto.getBranchId()).getData()==null){
        //    errors.add(new ErrorDto("branch","branch not found!"));
       // }
        return errors;
    }
}
