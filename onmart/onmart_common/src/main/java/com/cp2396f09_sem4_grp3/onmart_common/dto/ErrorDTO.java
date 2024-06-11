package com.cp2396f09_sem4_grp3.onmart_common.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {

    @JsonProperty("time_stamp")
    private Date timeStamp;
    private int status;
    private String path;
    
    @Builder.Default
    private List<String> errors = new ArrayList<>();

    public ErrorDTO() {
        this.errors = new ArrayList<>();
    }

    public void addErrors(String message){
        this.errors.add(message);
    }

}