package com.cp2396f09_sem4_grp3.onmart_service.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.VNPayUtils;

import lombok.Getter;

@Configuration
public class VNPayConfig {
    @Getter
    @Value("${app.vnpay.pay-url}")
    private String vnp_PayUrl;
    @Value("${app.vnpay.return-url}")
    private String vnp_ReturnUrl;
    @Value("${app.vnpay.tmn-code}")
    private String vnp_TmnCode;
    @Getter
    @Value("${app.vnpay.secret-key}")
    private String secretKey;
    @Value("${app.vnpay.version}")
    private String vnp_Version;
    @Value("${app.vnpay.command}")
    private String vnp_Command;
    @Value("${app.vnpay.order-type}")
    private String orderType;

    public Map<String, String> getVNPayConfig(String orderId, String email) {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", VNPayUtils.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtils.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl + "/" + orderId + "/" + email);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}
