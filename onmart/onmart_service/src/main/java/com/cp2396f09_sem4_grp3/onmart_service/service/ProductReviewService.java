package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductReviewRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductReviewResponse;

public interface ProductReviewService {
    Page<ProductReviewResponse> getAllProductReviews(Integer pageNo);

    Page<ProductReviewResponse> getAllReviewByProductId(Integer pageNo, Long productId);

    List<ProductReviewResponse> createReviewsByCustomer(List<ProductReviewRequest> requests, String email);

    void deleteReviewById(Long id);
}
