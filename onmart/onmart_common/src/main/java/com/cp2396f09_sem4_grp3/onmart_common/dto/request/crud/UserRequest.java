package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

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
public class UserRequest {

    private String email;

    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String phone;

    private ERole role;
    
    @JsonProperty("is_enabled")
    private boolean isEnabled;
}
