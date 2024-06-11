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

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/product-atrributes")
@RequiredArgsConstructor
public class InProductAttributeController {
    private final ProductAtrributeService atrributeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAtrributeResponse createProductAtrribute(
            @RequestBody ProductAtrributeRequest request) {
        return atrributeService.createProductAtrribute(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAtrributeResponse updateProductAtrribute(@PathVariable Long id,
            @RequestBody ProductAtrributeRequest request) {
        return atrributeService.updateProductAtrribute(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softProductAtrribute(@PathVariable Long id) {
        atrributeService.deleteProductAtrribute(id);
    }
}
