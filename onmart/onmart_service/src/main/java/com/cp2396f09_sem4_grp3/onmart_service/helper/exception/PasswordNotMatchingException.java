package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class PasswordNotMatchingException extends RuntimeException {
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}