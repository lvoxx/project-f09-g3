package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartRequest {
    @JsonProperty("product_id")
    private Long productId;
}
