package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.schedule;

import java.time.LocalDate;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.EDashboardType;
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
public class ScheduleDashboardResponse {
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private int month;
    private int year;
    @Builder.Default
    private EDashboardType type = EDashboardType.SCHEDULE;

    // Day of most schedule
    @JsonProperty("most_employee_works_in_day")
    private LocalDate mostEmployeeWorksInDay;
    @JsonProperty("number_of_employee_works_most_in_day")
    private Integer numberOfMostEmployeeWorksInDay;

    // Data
    @JsonProperty("shift_data")
    List<ScheduleDataByDay> shiftData;

    // Most employees worked in month
    @JsonProperty("top_most_employee_works_in_month")
    private List<EmployeeShiftCount> topMostEmployeeWorksInMonth;

    // Least employees worked in month
    @JsonProperty("top_least_employee_works_in_month")
    private List<EmployeeShiftCount> topLeastEmployeeWorksInMonth;

    // Total
    @JsonProperty("total_of_works_in_month")
    private Long totalOfWorksInMonth;
    @JsonProperty("total_of_works_at_morning_in_month")
    private Long totalOfWorksAtMorningInMonth;
    @JsonProperty("total_of_works_at_afternoon_in_month")
    private Long totalOfWorksAtAfternoonInMonth;
}
