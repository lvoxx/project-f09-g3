package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PublicProductResponse;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ImageUtils;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/products")
@RequiredArgsConstructor
public class ExProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PublicProductResponse> getProducts(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        if (!query.equals("")) {
            return productService.getAllPublicProductsByName(query, page, sort);
        }
        return productService.getAllPublicProducts(page, sort);
    }

    @GetMapping("/parent-categories")
    @ResponseStatus(HttpStatus.OK)
    public Page<PublicProductResponse> getProductByParentCategoryName(
            @RequestParam("query") String parentCategoryName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return productService.getAllPublicProductsByParentCategoryName(parentCategoryName, page, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublicProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable Long id) {
        ImageResponse response = productService.getProductImageById(id);

        return new ResponseEntity<>(response.getImageData(), ImageUtils.imagHeaders(response.getType()), HttpStatus.OK);
    }
}
 