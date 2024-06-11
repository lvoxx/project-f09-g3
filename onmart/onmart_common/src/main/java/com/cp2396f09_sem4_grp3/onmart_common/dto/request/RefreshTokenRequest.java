package com.cp2396f09_sem4_grp3.onmart_common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(name = "Refresh Token Request", description = "The refresh token request payload")
@Builder
public record RefreshTokenRequest(
                @JsonProperty("refresh_token") @NotBlank(message = "Refresh token can not be null or blank") @Schema(name = "refresh_token", nullable = false, description = "Refresh token's logined user") String refreshToken) {
}
