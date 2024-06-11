package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EScheduleShift;
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
public class ScheduleDetailsResponse {
    private Long id;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("shift")
    private EScheduleShift scheduleShift;
}
