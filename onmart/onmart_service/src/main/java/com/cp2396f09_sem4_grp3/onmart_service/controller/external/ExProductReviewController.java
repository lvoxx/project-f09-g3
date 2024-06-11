package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductReviewResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/product-reviews")
@RequiredArgsConstructor
public class ExProductReviewController {
    private final ProductReviewService reviewService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductReviewResponse> getAllReviews(@RequestParam(defaultValue = "0") Integer page) {
        return reviewService.getAllProductReviews(page);
    }

    @GetMapping("/{productId}/product")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductReviewResponse> getAllReviewsByProduct(
            @RequestParam(defaultValue = "0") Integer page,
            @PathVariable Long productId) {
        return reviewService.getAllReviewByProductId(page, productId);
    }

}
