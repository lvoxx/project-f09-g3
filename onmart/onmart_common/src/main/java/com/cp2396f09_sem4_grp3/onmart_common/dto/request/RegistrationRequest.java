package com.cp2396f09_sem4_grp3.onmart_common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationRequest(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String phone,
        String email,
        String password) {
}
