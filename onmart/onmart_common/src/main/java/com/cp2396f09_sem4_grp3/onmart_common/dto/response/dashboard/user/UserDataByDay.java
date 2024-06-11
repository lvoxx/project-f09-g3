package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.user;

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
public class UserDataByDay {
    private String day;
    @JsonProperty("numbers_of_regis")
    @Builder.Default
    private Long numbersOfRegis = 0L;
}
