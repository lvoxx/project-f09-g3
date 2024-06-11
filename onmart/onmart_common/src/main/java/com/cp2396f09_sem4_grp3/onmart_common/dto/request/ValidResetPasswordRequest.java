package com.cp2396f09_sem4_grp3.onmart_common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidResetPasswordRequest {

    @JsonProperty("new_password")
    @NotBlank(message = "New password should not be blank")
    @Schema(name = "new_password", nullable = false, allowableValues = "1Np9%b", description = "New user's password")
    private String newPassword;
}
