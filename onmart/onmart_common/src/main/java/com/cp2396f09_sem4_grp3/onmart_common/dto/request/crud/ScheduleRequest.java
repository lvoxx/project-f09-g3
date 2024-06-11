package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleRequest {
    private LocalDate date;
    
    @JsonProperty("schedule_details")
    private List<ScheduleDetailsRequest> scheduleDetails;
}
