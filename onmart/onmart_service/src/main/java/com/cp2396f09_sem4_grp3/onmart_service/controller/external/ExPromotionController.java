package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PromotionResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.PromotionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/promotions")
@RequiredArgsConstructor
public class ExPromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public Page<PromotionResponse> getPromotions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return promotionService.getAllPromotions(page, sort);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PromotionResponse getPromotionrById(@PathVariable Long id) {
        return promotionService.getPromotionById(id);
    }
}
