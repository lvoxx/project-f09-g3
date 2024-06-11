package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WarehouseDataByDay {
 private String day;

    @Builder.Default
    @JsonProperty("number_of_imports")
    private Long numberOfImports = 0L;

    @Builder.Default
    @JsonProperty("number_of_exports")
    private Long numberOfExports = 0L;

    public WarehouseDataByDay(String day) {
        this.day = day;
        this.numberOfImports = 0L;
        this.numberOfExports = 0L;
    }
}
