package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EmployeeShiftCount {
    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("shift_count")
    private Long shiftCount;
}
