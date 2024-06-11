package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/product-atrributes")
@RequiredArgsConstructor
public class ExProductAtrribute {
    private final ProductAtrributeService atrributeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductAtrributeResponse> getProductAtrribute() {
        return atrributeService.getAllProductAtrributes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductAtrributeResponse getProductAtrributeById(@PathVariable Long id) {
        return atrributeService.getProductAtrributeById(id);
    }
}