package com.cp2396f09_sem4_grp3.onmart_service.service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.VNPayResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    public VNPayResponse createVnPayPayment(HttpServletRequest request, String orderId, String email);
}
