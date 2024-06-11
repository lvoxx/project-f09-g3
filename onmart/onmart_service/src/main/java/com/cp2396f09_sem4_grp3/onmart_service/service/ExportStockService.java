package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ExportInvoiceRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ExportInvoiceResponse;

public interface ExportStockService {
    Page<ExportInvoiceResponse> getAllInvoices(Integer pageNo, String sortBy);

    ExportInvoiceResponse getInvoiceById(Long id);

    ExportInvoiceResponse createInvoice(String email, List<ExportInvoiceRequest> request);
}
