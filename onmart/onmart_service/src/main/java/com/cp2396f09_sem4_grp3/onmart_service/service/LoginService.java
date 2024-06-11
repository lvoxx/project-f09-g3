package com.cp2396f09_sem4_grp3.onmart_service.service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.LoginRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.RefreshTokenRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse signIn(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
