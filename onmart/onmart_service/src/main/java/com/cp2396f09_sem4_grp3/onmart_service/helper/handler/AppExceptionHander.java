package com.cp2396f09_sem4_grp3.onmart_service.helper.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cp2396f09_sem4_grp3.onmart_common.dto.ErrorDTO;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.*;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.*;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ErrorDTOWriter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AppExceptionHander {

    @Value("${app.path.fe.home}")
    private String fePath;

    @Value("${app.path.fe.registration-timeout}")
    private String feRegistrationTimeout;

    @Value("${app.path.fe.reset-password-timeout}")
    private String feResetPasswordTimeout;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            UserAlreadyVerifyException.class,
            PasswordNotMatchingException.class })
    public ErrorDTO registerDone(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.NOT_ACCEPTABLE, ex);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            DisabledUserLoginException.class,
            UnenbledUserException.class })
    public ErrorDTO accessAccountLocked(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.FORBIDDEN, ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorDTO loginFailed(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.UNAUTHORIZED, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidRefreshTokenException.class,
            InactivedPromotionException.class,
            OrderStateException.class })
    public ErrorDTO invalidToken(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.BAD_REQUEST, ex);
    }

    // NOTE: redirect user to FE that notif registration token timeout
    @ExceptionHandler(InvalidVerificationTokenException.class)
    public ResponseEntity<Void> redirectInvaliRegistrationToken(HttpServletRequest rq, RuntimeException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(this.fePath + this.feRegistrationTimeout));
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    // NOTE: redirect user to FE that notif reset password token timeout
    @ExceptionHandler(InvalidResetPasswordTokenException.class)
    public ResponseEntity<Void> redirectResetPasswordToken(HttpServletRequest rq, RuntimeException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(this.fePath + this.feResetPasswordTimeout));
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SendEmailFailedException.class)
    public ErrorDTO sendEmailFailed(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            UserNotFoundException.class,
            DeletedDataException.class,
            DataNotFoundException.class })
    public ErrorDTO notFoundResources(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.NOT_FOUND, ex);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ ExistDataException.class })
    public ErrorDTO duplicateData(HttpServletRequest rq, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTOWriter.writeError(rq, HttpStatus.NOT_FOUND, ex);
    }

}
