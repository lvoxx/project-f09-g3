package com.cp2396f09_sem4_grp3.onmart_service.service;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PaymentHistoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PaymentHistory;

public interface PaymentHistoryService {
    Page<PaymentHistoryResponse> getPaymentHistoryByCustomerId(String customerEmail, Integer page, String sort);

    PaymentHistoryResponse createPayment(PaymentHistory paymentHistory);
}
