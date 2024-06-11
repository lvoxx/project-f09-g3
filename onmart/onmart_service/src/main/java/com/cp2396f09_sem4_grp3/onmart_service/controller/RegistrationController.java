package com.cp2396f09_sem4_grp3.onmart_service.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.RegistrationRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.RegistrationResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.event.RegistrationCompleteEvent;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.AuthUtils;
import com.cp2396f09_sem4_grp3.onmart_service.service.RegistrationService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    @Value("${app.path.fe.home}")
    private String fePath;

    @Value("${app.path.fe.verify-success}")
    private String feVerifySuccess;

    private final UserService userService;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // NOTE: send email with token to registered email
    public RegistrationResponse registerUser(@RequestBody RegistrationRequest registrationRequest,
            final HttpServletRequest request) {
        User user = userService.addUserByGuest(registrationRequest);
        // Trigger an event to send registration email
        publisher.publishEvent(new RegistrationCompleteEvent(user, AuthUtils.applicationUrl(request)));

        return new RegistrationResponse("Success!  Please, check your email for to complete your registration",
                user.getId());
    }

    @GetMapping("/verify-user")
    // NOTE: redirect user to FE that notif verify success
    public ResponseEntity<Void> verifyEmail(@RequestParam("token") String token) {
        registrationService.verifyRegistrationEmail(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(this.fePath + this.feVerifySuccess));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // NOTE: resend email with token to registered email
    @GetMapping("/resend-verify-token")
    @ResponseStatus(HttpStatus.OK)
    public RegistrationResponse resendVerificationToken(@RequestParam("id") Long id,
            HttpServletRequest request) {
        return registrationService.resendVerifRegistrationEmail(id, request);
    }

}
