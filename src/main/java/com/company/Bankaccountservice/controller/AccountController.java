package com.company.Bankaccountservice.controller;


import com.company.Bankaccountservice.dto.AccountDto;
import com.company.Bankaccountservice.dto.ResponseDto;
import com.company.Bankaccountservice.service.AccountService;
import com.company.Bankaccountservice.util.SimpleCrud;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("account")

@Slf4j
@RequiredArgsConstructor
@RateLimiter(name = "first-rate-limiter", fallbackMethod = "fallBack")
public class AccountController implements SimpleCrud<Integer, AccountDto> {
    private final AccountService accountService;

    @PostMapping("/create")
    @Override
    @Operation(
            tags = "Create",
            summary = "Your summary create account method!",
            description = "This is method for create account!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = AccountDto.class
                            )
                    )
            )
    )
    public ResponseDto<AccountDto> create(@Valid @RequestBody AccountDto dto) {
        return this.accountService.create(dto);
    }

    @GetMapping("/get/{id}")
    @Override
    @Operation(
            tags = "Get",
            summary = "Your summary get by account method!",
            description = "This is method for get account!"
    )
    public ResponseDto<AccountDto> get(@PathVariable(value = "id") Integer id) {
        return this.accountService.get(id);
    }

    @Operation(
            tags = "Get",
            summary = "Your summary get-with-credit-card by account method!",
            description = "This is method for get-with-credit-card account!"
    )
    @GetMapping("/get-with-credit-card/{id}")
    public ResponseDto<AccountDto> getWithCreditCard(@PathVariable(value = "id") Integer id) {
        return this.accountService.getWithCreditCard(id);
    }

    @GetMapping("/get-by-loan/{id}")
    public ResponseDto<Set<AccountDto>> getAccountByLoan(@PathVariable Integer id) {
        return this.accountService.getAccountByLoan(id);
    }

    @GetMapping("/get-by-branch/{id}")
    public ResponseDto<Set<AccountDto>> getAccountByBranch(@PathVariable Integer id) {
        return this.accountService.getAccountByBranch(id);
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(
            tags = "Update",
            summary = "Your summary update account method!",
            description = "This is method for update account!",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = AccountDto.class
                            )
                    )
            )
    )
    public ResponseDto<AccountDto> update(@PathVariable(value = "id") Integer id, @RequestBody AccountDto dto) {
        return this.accountService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @Operation(
            tags = "Delete",
            summary = "Your summary delete by account method!",
            description = "This is method for delete account!"
    )
    public ResponseDto<AccountDto> delete(@PathVariable(value = "id") Integer id) {
        return this.accountService.delete(id);
    }

    @GetMapping("/get-all")
    @Operation(
            tags = "Universal search; Page!",
            summary = "Your summary get page by account method.",
            description = "This is method for get Page!",
            parameters = {
                    @Parameter(
                            name = "page",
                            example = "0",
                            in = ParameterIn.COOKIE
                    ),
                    @Parameter(
                            name = "size",
                            example = "10",
                            in = ParameterIn.COOKIE
                    )
            }
    )
    public ResponseDto<Page<AccountDto>> getAll(@RequestParam Map<String, String> params) {
        return this.accountService.getAll(params);
    }

    public ResponseDto<AccountDto> fallBack(Exception e) {
        log.warn("inti fallBack method");
        return ResponseDto.<AccountDto>builder()
                .message("inti fallBack method")
                .build();
    }
}
