package com.cp2396f09_sem4_grp3.onmart_service.helper.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.cp2396f09_sem4_grp3.onmart_common.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorDTOWriter {
    private ErrorDTOWriter() {
    }

    public static <E extends RuntimeException> ErrorDTO writeError (HttpServletRequest rq, HttpStatus status, E ex){
         ErrorDTO error = ErrorDTO.builder()
                .timeStamp(new Date())
                .status(status.value())
                .path(rq.getServletPath())
                .build();
        error.addErrors(ex.getMessage());
        return error;
    }
}
