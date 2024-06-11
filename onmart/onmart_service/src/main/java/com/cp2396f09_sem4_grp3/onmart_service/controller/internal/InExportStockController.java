package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ExportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ExportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ExportStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/export-stocks")
@RequiredArgsConstructor
public class InExportStockController {
    private final ExportStockService exportStockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExportInvoiceResponse createImportInvoice(@AuthenticationPrincipal UserDetails user,
            @RequestBody List<ExportInvoiceRequest> requests) {
        return exportStockService.createInvoice(user.getUsername(), requests);
    }

}
