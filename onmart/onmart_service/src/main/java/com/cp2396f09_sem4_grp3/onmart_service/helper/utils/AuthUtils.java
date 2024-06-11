package com.cp2396f09_sem4_grp3.onmart_service.helper.utils;

import java.io.UnsupportedEncodingException;

import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.VerificationToken;
import com.cp2396f09_sem4_grp3.onmart_service.event.listener.RegistrationCompleteEventListener;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {
    private AuthUtils() {
    }

    public static void resendVerificationTokenEmail(String applicationUrl, VerificationToken token, User user,
            RegistrationCompleteEventListener eventListener)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/verify-email?token=" + token.getToken();
        eventListener.resendVerificationEmail(url, user);
    }

    public static void passwordResetEmail(
            String passwordToken, User user,
            RegistrationCompleteEventListener eventListener)
            throws MessagingException, UnsupportedEncodingException {
        String url = "/" + passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url, user);
    }

    public static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
