package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PaymentHistoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PaymentHistory;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.repository.PaymentHistoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.PaymentHistoryService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final PaymentHistoryRepository repository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Page<PaymentHistoryResponse> getPaymentHistoryByCustomerId(String customerEmail, Integer page, String sort) {
        User customer = userService.findByEmail(customerEmail);
        Pageable paging = PageRequest.of(page, maxItems, Direction.DESC, sort);
        return repository.findPaymentHistoryByCustomerId(customer.getId(), paging)
                .map(payment -> modelMapper.map(payment, PaymentHistoryResponse.class));
    }

    @Override
    public PaymentHistoryResponse createPayment(PaymentHistory paymentHistory) {
        return modelMapper.map(repository.saveAndFlush(paymentHistory), PaymentHistoryResponse.class);
    }

}
