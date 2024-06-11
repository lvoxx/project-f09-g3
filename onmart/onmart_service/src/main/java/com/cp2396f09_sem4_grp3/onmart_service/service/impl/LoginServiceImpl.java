package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.LoginRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.RefreshTokenRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.LoginResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InvalidLoginException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InvalidRefreshTokenException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.UnenbledUserException;
import com.cp2396f09_sem4_grp3.onmart_service.security.JwtTokenProvider;
import com.cp2396f09_sem4_grp3.onmart_service.service.LoginService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {

    @Value("${app.messages.user.exception.trashed-user}")
    private String exTrashedUser;
    @Value("${app.messages.user.exception.unenabled-user}")
    private String exUnenabledUser;
    @Value("${app.messages.user.exception.incorrect-login}")
    private String exUncorrectLogin;
    @Value("${app.messages.user.exception.invalid-refresh-token}")
    private String exInvalidRefreshToken;

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse signIn(LoginRequest loginRequest) {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        } catch (AuthenticationException e) {
            throw new InvalidLoginException(e.getMessage());
        }

        var user = userService.findByEmail(loginRequest.email());
        if (!user.isEnabled()) {
            throw new UnenbledUserException(this.exUnenabledUser);
        }
        // Generate access token
        var accessToken = tokenProvider.generateToken(user);
        // Generate refresh token
        var refreshToken = tokenProvider.generateRefreshToken(new HashMap<>(), user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = tokenProvider.extractUsername(refreshTokenRequest.refreshToken());
        User user = userService.findByEmail(userEmail);
        if (!user.isEnabled()) {
            throw new UnenbledUserException(this.exUnenabledUser);
        }
        if (!tokenProvider.isTokenValid(refreshTokenRequest.refreshToken(), user)) {
            throw new InvalidRefreshTokenException(this.exInvalidRefreshToken);
        }
        // Generate new access token
        var jwt = tokenProvider.generateToken(user);

        return LoginResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshTokenRequest.refreshToken()) // Give user old refresh token
                .build();

    }
}
