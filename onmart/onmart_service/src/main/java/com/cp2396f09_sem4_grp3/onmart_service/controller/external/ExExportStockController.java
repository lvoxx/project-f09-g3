package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ExportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ExportStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/export-stocks")
@RequiredArgsConstructor
public class ExExportStockController {
    private final ExportStockService exportStockService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ExportInvoiceResponse> getAllImportInvoices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return exportStockService.getAllInvoices(page, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExportInvoiceResponse getImportInvoiceById(@PathVariable Long id) {
        return exportStockService.getInvoiceById(id);
    }
}
