package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.Optional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.ValidResetPasswordRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ResetPasswordResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PasswordResetToken;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;

public interface PasswordResetTokenService {

    String validatePasswordResetToken(String passwordResetToken);

    Optional<User> findUserByPasswordToken(String passwordResetToken);

    PasswordResetToken findPasswordResetToken(String token);

    // Actual reset password service on LoginController
    ResetPasswordResponse requestResetPasswordSession(String email);

    ResetPasswordResponse validResetPasswordRequest(ValidResetPasswordRequest request,
            String token);

    

}
