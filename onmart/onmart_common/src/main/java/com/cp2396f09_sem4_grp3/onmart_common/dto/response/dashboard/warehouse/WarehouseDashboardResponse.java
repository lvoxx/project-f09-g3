package com.cp2396f09_sem4_grp3.onmart_common.dto.response.dashboard.warehouse;

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
public class WarehouseDashboardResponse {
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private int month;
    private int year;
    @Builder.Default
    private EDashboardType type = EDashboardType.WAREHOUSE;

    // Most Import - Export
    @JsonProperty("most_import_day")
    private LocalDate mostImportDay;
    @JsonProperty("number_of_most_import_in_day")
    private Integer numberOfMostImportInDay;
    @JsonProperty("most_export_day")
    private LocalDate mostExportDay;
    @JsonProperty("number_of_most_export_in_day")
    private Integer numberOfMostExportInDay;

    // IMPORT-EXPORT
    @JsonProperty("warehouse_data")
    private List<WarehouseDataByDay> warehouseData;

    // WARNING PRODUCT
    @JsonProperty("limit_for_out_of_stock")
    private Integer limitForOutOfStock;
    @JsonProperty("product_may_be_out_of_stock")
    private List<WarehouseWarning> productMayBeOutOfStock;

    // GENERAL
    @JsonProperty("number_of_exports_in_month")
    private int numberOfExportsInMonth;
    @JsonProperty("number_of_products_export_in_month")
    private Long numberOfProductsExportInMonth;
    @JsonProperty("number_of_imports_in_month")
    private int numberOfImportsInMonth;
    @JsonProperty("number_of_products_import_in_month")
    private Long numberOfProductsImportInMonth;

    // TOTAL
    @JsonProperty("total_in_stock")
    private Long totalInStock;
    @JsonProperty("total_in_sell")
    private Long totalInSell;
}
