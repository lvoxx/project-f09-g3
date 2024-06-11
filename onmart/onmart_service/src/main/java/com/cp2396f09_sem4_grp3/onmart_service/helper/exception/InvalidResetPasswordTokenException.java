package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class InvalidResetPasswordTokenException extends RuntimeException {
    public InvalidResetPasswordTokenException(String message) {
        super(message);
    }
}