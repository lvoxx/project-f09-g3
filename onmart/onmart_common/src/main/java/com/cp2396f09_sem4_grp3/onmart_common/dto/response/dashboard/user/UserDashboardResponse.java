package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user;

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
public class UserDashboardResponse {
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private int month;
    private int year;
    @Builder.Default
    private EDashboardType type = EDashboardType.USERS;

    // REGISTRATION
    @JsonProperty("most_registered_day")
    private LocalDate mostRegisteredDay;
    @JsonProperty("number_of_most_regis")
    private Integer numberOfRegistrationInDay;
    @JsonProperty("number_of_regis")
    private Integer numberOfRegistrationInMonth;
    @JsonProperty("registered_data")
    private List<UserDataByDay> registeredData;

    // DISABLED
    @JsonProperty("most_disabled_day")
    private LocalDate mostDisabledDay;
    @JsonProperty("number_of_most_disable")
    private Integer numberOfDisableInDay;
    @JsonProperty("number_of_disable")
    private Integer numberOfDisableInMonth;
    @JsonProperty("disable_data")
    private List<UserDataByDay> disableData;

    // GENERAL
    @JsonProperty("number_of_accounts")
    private Long numberOfAccounts;
    @JsonProperty("number_of_admin")
    private Long numberOfAdmin;
    @JsonProperty("number_of_sellers")
    private Long numberOfSellers;
    @JsonProperty("number_of_warehouce_staffs")
    private Long numberOfWarehouceStaffs;
    @JsonProperty("number_of_customers")
    private Long numberOfCustomers;
}
