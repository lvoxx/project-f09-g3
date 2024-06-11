package com.cp2396f09_sem4_grp3.onmart_common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(name = "Login Request", description = "The login request payload")
@Builder
public record LoginRequest(
                @NotBlank(message = "Login email can be null but not blank") @Schema(name = "email", nullable = false, allowableValues = "example.email@mail.com") String email,

                @NotBlank(message = "Login password cannot be blank") @Schema(name = "password", nullable = false, allowableValues = "M32s&c") String password) {

}
