package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductReviewRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductReviewResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/product-reviews")
@RequiredArgsConstructor
public class InProductReviewController {
    private final ProductReviewService reviewService;

    @PostMapping
    public List<ProductReviewResponse> createReview(@RequestBody List<ProductReviewRequest> requests,
            @AuthenticationPrincipal UserDetails user) {
        return reviewService.createReviewsByCustomer(requests, user.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }
}
