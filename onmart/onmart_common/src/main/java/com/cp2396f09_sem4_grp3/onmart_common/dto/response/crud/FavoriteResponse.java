package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteResponse {
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String productName;
}
