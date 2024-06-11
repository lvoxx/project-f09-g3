package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
