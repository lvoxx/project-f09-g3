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

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ImportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ImportInvoiceResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ImportStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/import-stocks")
@RequiredArgsConstructor
public class InImportStockController {
    private final ImportStockService importStockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImportInvoiceResponse createImportInvoice(@AuthenticationPrincipal UserDetails user,
            @RequestBody List<ImportInvoiceRequest> requests) {
        return importStockService.createInvoice(user.getUsername(), requests);
    }

}
