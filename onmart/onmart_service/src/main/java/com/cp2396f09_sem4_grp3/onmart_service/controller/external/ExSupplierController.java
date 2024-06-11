package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.SupplierResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/suppliers")
@RequiredArgsConstructor
public class ExSupplierController {
    private final SupplierService supplierService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SupplierResponse getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }
}
