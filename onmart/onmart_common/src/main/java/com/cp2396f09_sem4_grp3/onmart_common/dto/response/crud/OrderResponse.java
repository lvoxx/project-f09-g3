package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;
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
public class OrderResponse{

    private Long id;

    private EOrderState state;

    private String note;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("delivery_fee")
    private BigDecimal deliveryFee;

    @JsonProperty("delivery_address")
    private String deliveryAddress;

    @JsonProperty("reject_reason")
    private String rejectReason;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("loyalty_points_pay")
    private String loyaltyPointsPay;

    @JsonProperty("payment_history")
    private PaymentHistoryResponse paymentHistory;

    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;
}