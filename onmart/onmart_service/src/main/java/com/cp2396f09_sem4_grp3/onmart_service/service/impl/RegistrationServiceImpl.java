package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.RegistrationResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.VerificationToken;
import com.cp2396f09_sem4_grp3.onmart_service.event.listener.RegistrationCompleteEventListener;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InvalidVerificationTokenException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.SendEmailFailedException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.UserAlreadyVerifyException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.AuthUtils;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.VerificationTokenRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.RegistrationService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Value("${app.messages.user.response.verify-email}")
    private String messVerifyEmail;
    @Value("${app.messages.user.response.resend-email}")
    private String messResendEmail;
    @Value("${app.messages.user.exception.trashed-user}")
    private String exTrashedUser;
    @Value("${app.messages.user.exception.user-already-verify}")
    private String exUserAlreadyVerify;

    private final UserService userService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;

    @Override
    public RegistrationResponse verifyRegistrationEmail(String token) {
        VerificationToken theToken = tokenRepository.findByToken(token).get();
        if (theToken.getUser().isEnabled()) {
            throw new UserAlreadyVerifyException(this.exUserAlreadyVerify);
        }
        String verificationResult = userService.validateToken(token);
        if (!verificationResult.equalsIgnoreCase("valid")) {
            throw new InvalidVerificationTokenException("Invalid verification token");
        }
        return new RegistrationResponse(this.messVerifyEmail,
                theToken.getUser().getId());
    }

    @Override
    public RegistrationResponse resendVerifRegistrationEmail(Long id, HttpServletRequest request) {
        User user = userRepository.findById(id).get();
        if (user.isEnabled()) {
            throw new UserAlreadyVerifyException(this.exUserAlreadyVerify);
        }
        String oldToken = user.getVerificationToken().getToken();
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        try {
            AuthUtils.resendVerificationTokenEmail(AuthUtils.applicationUrl(request), verificationToken,
                    user, eventListener);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new SendEmailFailedException(e.getMessage());
        }

        return new RegistrationResponse(this.messResendEmail, verificationToken.getUser().getId());
    }

}
