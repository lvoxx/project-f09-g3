package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ImportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ImportStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/import-stocks")
@RequiredArgsConstructor
public class ExImportStockController {
    private final ImportStockService importStockService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ImportInvoiceResponse> getAllImportInvoices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return importStockService.getAllInvoices(page, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImportInvoiceResponse getImportInvoiceById(@PathVariable Long id) {
        return importStockService.getInvoiceById(id);
    }
}
