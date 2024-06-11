package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.SupplierRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.SupplierResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/suppliers")
@RequiredArgsConstructor
public class InSupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierResponse createSupplier(@RequestBody SupplierRequest supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierResponse updateSupplier(@PathVariable Long id, @RequestBody SupplierRequest request) {
        return supplierService.updateSupplier(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
