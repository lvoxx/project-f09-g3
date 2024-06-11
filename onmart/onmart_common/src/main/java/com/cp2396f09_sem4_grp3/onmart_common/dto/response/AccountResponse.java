package com.cp2396f09_sem4_grp3.onmart_common.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.AddressResponse;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AccountResponse {

    private Long id;
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private List<AddressResponse> address;
    @JsonProperty("loyaty_points")
    private BigDecimal loyatyPoints;
}
