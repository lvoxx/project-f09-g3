package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.EDashboardType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDashboardResponse {
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private int month;
    private int year;
    @Builder.Default
    private EDashboardType type = EDashboardType.ORDERS;

    // Order placed
    @JsonProperty("most_order_day")
    private LocalDate mostOrderDay;
    @JsonProperty("number_of_most_order")
    private Integer numberOfMostOrderInDay;

    //  REVENUE
    // Find all orders in the month
    private BigDecimal revenue;
    // Find all canceled orders in the month
    @JsonProperty("loyaty_points_callback")
    private BigDecimal loyatyPointsCallback;
    // = revenue - loyatyPointsCallback
    @JsonProperty("true_revenue")
    private BigDecimal trueRevenue;
    @JsonProperty("order_data")
    private List<OrderDataByDay> orderData;

    // GENERAL
    @JsonProperty("number_of_orders_in_month")
    private Long numberOfOrdersInMonth;
    @JsonProperty("number_of_placed_orders_in_month")
    private Long numberOfPlacedOrdersInMonth;
    @JsonProperty("number_of_preparing_orders_in_month")
    private Long numberOfPreparingOrdersInMonth;
    @JsonProperty("number_of_delivering_orders_in_month")
    private Long numberOfDeliveringOrdersInMonth;
    @JsonProperty("number_of_delivered_orders_in_month")
    private Long numberOfDeliveredOrdersInMonth;
    @JsonProperty("number_of_delivery_failed_orders_in_month")
    private Long numberOfDeliveryFailedOrdersInMonth;
    @JsonProperty("number_of_received_orders_in_month")
    private Long numberOfReceivedOrdersInMonth;
    @JsonProperty("number_of_canceled_orders_in_month")
    private Long numberOfCanceledOrdersInMonth;
}
