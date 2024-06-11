package com.cp2396f09_sem4_grp3.onmart_service.service;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.PromotionRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PromotionResponse;

public interface PromotionService {

    Page<PromotionResponse> getAllPromotions(Integer pageNo, String sortBy);

    PromotionResponse getPromotionById(Long id);

    PromotionResponse createPromotion(PromotionRequest request);

    PromotionResponse updatePromotion(PromotionRequest request, Long id);

    void deletePromotion(Long id);

    void updateExpiredPromotions();
}
