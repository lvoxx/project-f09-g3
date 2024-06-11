package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.SupplierRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.SupplierResponse;

public interface SupplierService {

    List<SupplierResponse> getAllSuppliers();

    SupplierResponse getSupplierById(Long id);

    SupplierResponse createSupplier(SupplierRequest request);

    SupplierResponse updateSupplier(SupplierRequest request, Long id);

    void deleteSupplier(Long id);
}
