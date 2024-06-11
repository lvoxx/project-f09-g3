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

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.PromotionRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PromotionResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.PromotionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/promotions")
@RequiredArgsConstructor
public class InPromotionController {
    private final PromotionService promotionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionResponse createPromotion(@RequestBody PromotionRequest promotion) {
        return promotionService.createPromotion(promotion);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionResponse updatePromotion(@PathVariable Long id, @RequestBody PromotionRequest request) {
        return promotionService.updatePromotion(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
    }
}
