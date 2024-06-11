package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class DisabledUserLoginException extends RuntimeException {
    public DisabledUserLoginException(String message) {
        super(message);
    }
}