package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDataByDay {
    private String day;

    @Builder.Default
    @JsonProperty("number_of_placed_orders")
    private Long numberOfPlacedOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_preparing_orders")
    private Long numberOfPreparingOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_delivering_orders")
    private Long numberOfDeliveringOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_delivered_orders")
    private Long numberOfDeliveredOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_delivery_failed_orders")
    private Long numberOfDeliveryFailedOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_received_orders")
    private Long numberOfReceivedOrders = 0L;

    @Builder.Default
    @JsonProperty("number_of_canceled_orders")
    private Long numberOfCanceledOrders = 0L;

    public OrderDataByDay(String day) {
        this.day = day;
        this.numberOfPlacedOrders = 0L;
        this.numberOfPreparingOrders = 0L;
        this.numberOfDeliveringOrders = 0L;
        this.numberOfDeliveredOrders = 0L;
        this.numberOfDeliveryFailedOrders = 0L;
        this.numberOfReceivedOrders = 0L;
        this.numberOfCanceledOrders = 0L;
    }

    public LocalDate getLocalDate(DateTimeFormatter formatter) {
        return LocalDate.parse(day, formatter);
    }
}
