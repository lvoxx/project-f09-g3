package com.cp2396f09_sem4_grp3.onmart_service.helper.exception;

public class SendEmailFailedException extends RuntimeException {
    public SendEmailFailedException(String message) {
        super(message);
    }
}