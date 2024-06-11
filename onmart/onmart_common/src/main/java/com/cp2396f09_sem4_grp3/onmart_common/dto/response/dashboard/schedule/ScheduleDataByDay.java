package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ScheduleDataByDay {
    private String day;

    @Builder.Default
    @JsonProperty("number_of_morning_shift")
    private Long numberOfMorningShift = 0L;

    @Builder.Default
    @JsonProperty("number_of_afternoon_shift")
    private Long numberOfAfternoonShift = 0L;

    public ScheduleDataByDay(String day) {
        this.day = day;
        this.numberOfMorningShift = 0L;
        this.numberOfAfternoonShift = 0L;
    }
}
