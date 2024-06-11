package com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud;

public class DeletedDataException extends RuntimeException {
    public DeletedDataException(String message) {
        super(message);
    }
}