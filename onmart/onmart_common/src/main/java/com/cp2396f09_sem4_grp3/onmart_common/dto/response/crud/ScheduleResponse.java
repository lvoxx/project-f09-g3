package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleResponse {
    private Long id;

    @JsonProperty("admin_id")
    private Long adminId;

    @JsonProperty("admin_name")
    private String adminName;

     private LocalDate date;

    @JsonProperty("schedule_details")
    private List<ScheduleDetailsResponse> scheduleDetails;
}
