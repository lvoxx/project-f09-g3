package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.ValidResetPasswordRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ResetPasswordResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.PasswordResetToken;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.event.listener.RegistrationCompleteEventListener;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InvalidResetPasswordTokenException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.SendEmailFailedException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.AuthUtils;
import com.cp2396f09_sem4_grp3.onmart_service.repository.PasswordResetTokenRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.PasswordResetTokenService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Value("${app.messages.user.service.valid}")
    private String validTokenMess;
    @Value("${app.messages.user.exception.trashed-user}")
    private String exTrashedUser;
    @Value("${app.messages.user.exception.user-already-verify}")
    private String exUserAlreadyVerify;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private UserService userService;
    private final RegistrationCompleteEventListener eventListener;

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if (passwordToken == null) {
            throw new InvalidResetPasswordTokenException("Invalid verification token");
        }
        Calendar calendar = Calendar.getInstance();
        log.info(String.valueOf(calendar.getTime().getTime()));
        log.info(String.valueOf(passwordToken.getExpirationTime().getTime()));
        if ((passwordToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            throw new InvalidResetPasswordTokenException("Link already expired, resend link");
        }
        return this.validTokenMess;
    }

    @Override
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    @Override
    public PasswordResetToken findPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public ResetPasswordResponse requestResetPasswordSession(String email) {
        // Only get email to generate reset password email
        User user = userService.findByEmail(email);
        String passwordResetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, passwordResetToken);
        // Sent reset password email
        try {
            AuthUtils.passwordResetEmail(
                    passwordResetToken, user, eventListener);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new SendEmailFailedException("Can not sent reset request to email: " + user.getEmail());
        }
        return new ResetPasswordResponse("Reset password send!", user.getEmail());
    }

    @Override
    public ResetPasswordResponse validResetPasswordRequest(ValidResetPasswordRequest request, String token) {
        // Valid reset password token
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            throw new InvalidResetPasswordTokenException("Invalid token password reset token");
        }
        User user = userService.findUserByPasswordToken(token);
        userService.changePassword(user, request.getNewPassword());
        return new ResetPasswordResponse("Password has been reset successfully", user.getEmail());
    }

}
