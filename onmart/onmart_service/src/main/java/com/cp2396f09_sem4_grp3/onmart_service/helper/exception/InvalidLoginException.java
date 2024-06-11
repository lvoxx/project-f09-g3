package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}