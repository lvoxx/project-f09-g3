package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PromotionRequest {
    private String name;
    @JsonProperty("discount_percent")
    private int discountPercent;
    @JsonProperty("start_date")
    private ZonedDateTime startDate;
    @JsonProperty("end_date")
    private ZonedDateTime endDate;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("product_ids")
    private List<Long> productIds;
}
