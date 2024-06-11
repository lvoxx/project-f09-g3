package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductAtrributeResponse {
    private Long id;
    private String name;

    @JsonProperty("price_difference")
    private BigDecimal priceDifference;

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonManagedReference
    private Set<Product> products = new HashSet<>();
}
