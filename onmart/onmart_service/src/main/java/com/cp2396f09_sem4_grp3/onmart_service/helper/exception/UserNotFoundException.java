package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}