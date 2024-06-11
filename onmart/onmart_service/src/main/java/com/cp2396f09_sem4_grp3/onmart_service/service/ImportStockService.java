package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ImportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ImportInvoiceResponse;

public interface ImportStockService {
    Page<ImportInvoiceResponse> getAllInvoices(Integer pageNo, String sortBy);

    ImportInvoiceResponse getInvoiceById(Long id);

    ImportInvoiceResponse createInvoice(String email, List<ImportInvoiceRequest> request);
}
