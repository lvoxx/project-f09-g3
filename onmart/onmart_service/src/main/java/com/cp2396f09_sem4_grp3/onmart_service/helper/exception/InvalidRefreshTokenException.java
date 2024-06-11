package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}