package com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}