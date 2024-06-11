package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItemResponse {
    private Long id;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("discount_percent")
    private int discountPercent;

    @JsonProperty("discount_name")
    private String discountName;

    @JsonProperty("product_price")
    private BigDecimal productPrice;

    @JsonProperty("sub_total_price")
    private BigDecimal subTotalPrice;
}