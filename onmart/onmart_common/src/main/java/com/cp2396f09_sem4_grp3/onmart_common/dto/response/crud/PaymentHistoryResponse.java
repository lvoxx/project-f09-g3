package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EPaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryResponse{

    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("amount")
    private BigDecimal amount;

    private String currency;

    private String description;

    @JsonProperty("payment_method")
    private EPaymentMethod paymentMethod;
}