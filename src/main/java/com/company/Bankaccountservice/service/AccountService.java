package com.company.Bankaccountservice.service;

import com.company.Bankaccountservice.dto.AccountDto;
import com.company.Bankaccountservice.dto.ErrorDto;
import com.company.Bankaccountservice.dto.ResponseDto;
import com.company.Bankaccountservice.repository.AccountRepository;
import com.company.Bankaccountservice.service.mapper.AccountMapper;
import com.company.Bankaccountservice.service.validate.AccountValidate;
import com.company.Bankaccountservice.util.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements SimpleCrud<Integer, AccountDto> {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountValidate accountValidate;

    @Override
    public ResponseDto<AccountDto> create(AccountDto dto) {
        List<ErrorDto> errors = this.accountValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<AccountDto>builder()
                    .message("Validate error!")
                    .errors(errors)
                    .code(-2)
                    .build();
        }
        try {
            return ResponseDto.<AccountDto>builder()
                    .message("Account successful created!")
                    .data(this.accountMapper.toDto(accountRepository.save(accountMapper.toEntity(dto))))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While saving error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<AccountDto> get(Integer id) {
        return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id)
                .map(account -> ResponseDto.<AccountDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.accountMapper.toDto(account))
                        .build())
                .orElse(ResponseDto.<AccountDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<AccountDto> update(Integer id, AccountDto dto) {
        try {
            return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id).map(account -> {
                this.accountMapper.update(account, dto);
                this.accountRepository.save(account);
                return ResponseDto.<AccountDto>builder()
                        .message("Account successful updated!")
                        .success(true)
                        .data(this.accountMapper.toDto(account))
                        .build();
            }).orElse(ResponseDto.<AccountDto>builder()
                    .message("Not found!")
                    .code(-1)
                    .build());
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While updating error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    @Override
    public ResponseDto<AccountDto> delete(Integer id) {
        try {
            return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id).map(account -> {
                        account.setDeletedAt(LocalDateTime.now());
                        this.accountRepository.save(account);
                        return ResponseDto.<AccountDto>builder()
                                .message("Account successful deleted!")
                                .success(true)
                                .data(this.accountMapper.toDto(account))
                                .build();
                    })
                    .orElse(ResponseDto.<AccountDto>builder()
                            .message("Not found!")
                            .code(-1)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<AccountDto>builder()
                    .message("While deleting error " + e.getMessage())
                    .code(-3)
                    .build();
        }
    }

    public ResponseDto<Page<AccountDto>> getAll(Map<String, String> params) {
        int page = 0, size = 10;
        if (params.containsKey("page")){
            page=Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")){
            size=Integer.parseInt(params.get("size"));
        }
        Page<AccountDto> accounts=this.accountRepository.findByAccountsByPaginationSearch(
                params.get("accountId")==null?null:Integer.valueOf(params.get("accountId")),
                params.get("balance")==null?null:Double.valueOf(params.get("balance")),
                params.get("type"),
                params.get("branchId")==null?null:Integer.valueOf(params.get("branchId")),
                PageRequest.of(page,size)).map(accountMapper::toDto);
        return ResponseDto.<Page<AccountDto>>builder()
                .message("Ok")
                .success(true)
                .data(accounts)
                .build();
    }

    public ResponseDto<AccountDto> getWithCreditCard(Integer id) {
        return this.accountRepository.findByAccountIdAndDeletedAtIsNull(id)
                .map(account -> ResponseDto.<AccountDto>builder()
                        .message("OK")
                        .success(true)
                        .data(this.accountMapper.toDtoWithCreditCard(account))
                        .build())
                .orElse(ResponseDto.<AccountDto>builder()
                        .message("Not found!")
                        .code(-1)
                        .build());
    }
    public ResponseDto<Set<AccountDto>> getAccountByLoan(Integer id) {
        return ResponseDto.<Set<AccountDto>>builder()
                .message("Ok")
                .success(true)
                .data(accountRepository.findAllByLoanIdAndDeletedAtIsNull(id).stream().map(accountMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
    public ResponseDto<Set<AccountDto>> getAccountByBranch(Integer id) {
        return ResponseDto.<Set<AccountDto>>builder()
                .message("Ok")
                .success(true)
                .data(accountRepository.findAllByBranchIdAndDeletedAtIsNull(id).stream().map(accountMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
    public ResponseDto<Set<AccountDto>> getAccountByTransaction(Integer id){
        return ResponseDto.<Set<AccountDto>>builder()
                .message("Ok")
                .success(true)
                .data(accountRepository.findAllByTransactionIdAndDeletedAtIsNull(id).stream().map(this.accountMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}