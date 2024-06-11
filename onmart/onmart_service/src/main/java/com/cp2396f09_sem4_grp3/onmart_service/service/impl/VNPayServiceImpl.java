package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.VNPayResponse;
import com.cp2396f09_sem4_grp3.onmart_service.config.VNPayConfig;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.VNPayUtils;
import com.cp2396f09_sem4_grp3.onmart_service.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {
    private final VNPayConfig vnPayConfig;

    @Override
    public VNPayResponse createVnPayPayment(HttpServletRequest request, String orderId, String email) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(orderId, email);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(request));
        // build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

}
