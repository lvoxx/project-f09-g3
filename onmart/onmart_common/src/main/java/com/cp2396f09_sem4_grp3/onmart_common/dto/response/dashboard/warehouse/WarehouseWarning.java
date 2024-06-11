package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WarehouseWarning {
    @JsonProperty("product_id")
    private Long id;
    @JsonProperty("product_name")
    private String name;
    @JsonProperty("in_sell_quantity")
    private int inSellQuantity;
    @JsonProperty("in_stock_quantity")
    private int inStockQuantity;
}
