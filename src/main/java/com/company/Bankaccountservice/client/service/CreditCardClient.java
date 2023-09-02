package com.company.Bankaccountservice.client.service;

import com.company.Bankaccountservice.client.dto.CreditCardDto;
import com.company.Bankaccountservice.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "banker-service",path = "/banker-service/credit-card")
public interface CreditCardClient {
    @GetMapping("/get-by-account/{id}")
    ResponseDto<Set<CreditCardDto>> getCreditCardsByAccountId(@PathVariable(value = "id") Integer id);
}
