package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ExportInvoiceDetailsResponse {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private ExportInvoiceResponse exportStockInvoice;
}
