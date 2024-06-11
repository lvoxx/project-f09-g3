package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PromotionProductResponse {
    private Long id;
    private String name;
    @JsonProperty("discount_percent")
    private int discountPercent;
    @JsonProperty("start_date")
    private ZonedDateTime startDate;
    @JsonProperty("end_date")
    private ZonedDateTime endDate;
}
