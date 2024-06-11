package com.cp2396f09_sem4_grp3.onmart_common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to change password with valid token send in user's reset password email")
public class ChangePasswordRequest {
    @JsonProperty("old_password")
    @NotBlank(message = "Old password should not be blank")
    @Schema(name = "old_password", nullable = false, allowableValues = "M32s&c", description = "Old user's password")
    private String oldPassword;

    @NotBlank(message = "New password should not be blank")
    @Schema(name = "new_password", nullable = false, allowableValues = "qwT%Il", description = "New user's password")
    @JsonProperty("new_password")
    private String newPassword;
}
