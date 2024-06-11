package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.ERole;
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
public class UserResponse {

    private Long id;

    private String email;

    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String phone;

    @JsonProperty("loyaty_points")
    private BigDecimal loyatyPoints;

    private ERole role;

    @JsonProperty("is_enabled")
    private boolean isEnabled;

    @JsonProperty("is_trashed")
    private boolean isTrashed;

    private List<AddressResponse> address;
}
