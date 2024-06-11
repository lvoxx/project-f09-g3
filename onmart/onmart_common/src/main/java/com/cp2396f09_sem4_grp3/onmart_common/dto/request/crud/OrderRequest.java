package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequest {
    @JsonProperty("loyalty_points_pay")
    private BigDecimal loyaltyPointsPay;
    @JsonProperty("delivery_fee")
    private BigDecimal deliveryFee;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("delivery_address")
    private String deliveryAddress;
    private String note;
    @JsonProperty("order_details")
    private List<OrderDetailsRequest> orderDetails;
}
