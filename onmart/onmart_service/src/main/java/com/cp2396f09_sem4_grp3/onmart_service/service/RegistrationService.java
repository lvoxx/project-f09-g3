package com.cp2396f09_sem4_grp3.onmart_service.service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.RegistrationResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface RegistrationService {
    RegistrationResponse verifyRegistrationEmail(String token);
    RegistrationResponse resendVerifRegistrationEmail(Long id, HttpServletRequest request);
}
