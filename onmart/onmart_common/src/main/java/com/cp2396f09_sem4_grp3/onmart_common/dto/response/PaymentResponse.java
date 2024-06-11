package com.cp2396f09_sem4_grp3.onmart_common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentResponse {
    @JsonProperty("payment_url")
    private String paymentUrl;
}
