package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PaymentHistoryResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.PaymentHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/payment-history")
@RequiredArgsConstructor
public class InPaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/account")
    public Page<PaymentHistoryResponse> listAllPaymentHistoryByAccount(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return paymentHistoryService.getPaymentHistoryByCustomerId(user.getUsername(), page, sort);
    }

}
