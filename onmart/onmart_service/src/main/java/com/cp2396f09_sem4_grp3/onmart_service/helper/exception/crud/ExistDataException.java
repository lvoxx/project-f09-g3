package com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud;

public class ExistDataException extends RuntimeException {
    public ExistDataException(String message) {
        super(message);
    }
}