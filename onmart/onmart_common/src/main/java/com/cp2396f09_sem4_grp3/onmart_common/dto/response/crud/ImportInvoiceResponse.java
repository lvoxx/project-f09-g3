package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ImportInvoiceResponse {
    private Long id;
    private Long creatorId;
    private String creatorName;
    private List<ImportInvoiceDetailsResponse> importStockInvoiceDetails;
}