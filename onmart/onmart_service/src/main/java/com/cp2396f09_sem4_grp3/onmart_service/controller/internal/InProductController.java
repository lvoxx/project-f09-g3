package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PublicProductResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/products")
@RequiredArgsConstructor
public class InProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublicProductResponse createProduct(@ModelAttribute ProductRequest product) throws IOException {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PublicProductResponse updateProduct(@PathVariable Long id, @ModelAttribute ProductRequest request) {
        return productService.updateProduct(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
