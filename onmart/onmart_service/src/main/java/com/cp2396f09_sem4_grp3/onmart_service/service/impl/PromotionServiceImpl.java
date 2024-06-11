package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.PromotionRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PromotionResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Promotion;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.InactivedPromotionException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.PromotionRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.PromotionService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<PromotionResponse> getAllPromotions(Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
        Page<Promotion> pagedResult = promotionRepository.findAll(paging);

        return pagedResult.map(supplier -> modelMapper.map(supplier, PromotionResponse.class));
    }

    @Override
    public PromotionResponse getPromotionById(Long id) {
        return modelMapper.map(promotionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No supplier promotion with id " + id)),
                PromotionResponse.class);
    }

    @Override
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .name(request.getName())
                .discountPercent(request.getDiscountPercent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(request.isActive())
                .build();
        Promotion response = promotionRepository.saveAndFlush(promotion);
        if (!request.getProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(request.getProductIds());
            products.forEach(product -> product.setPromotion(promotion));
            productRepository.saveAllAndFlush(products);
        }

        return modelMapper.map(response, PromotionResponse.class);
    }

    @Override
    public PromotionResponse updatePromotion(PromotionRequest request, Long id) {
        Promotion oldPromotion = promotionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No promotion found."));

        oldPromotion.setName(request.getName());
        oldPromotion.setDiscountPercent(request.getDiscountPercent());
        oldPromotion.setStartDate(request.getStartDate());
        oldPromotion.setEndDate(request.getEndDate());
        oldPromotion.setActive(request.isActive());

        // If promotion is inactive or end date is before now, remove all product
        // relationships
        if (!request.isActive() || request.getEndDate().isBefore(ZonedDateTime.now())) {
            if (request.getProductIds() != null) {
                throw new InactivedPromotionException("Promotion is inactive, cannot associate products with it.");
            }
            // Remove promotion from all associated products
            oldPromotion.getProducts().forEach(product -> product.setPromotion(null));
            productRepository.saveAllAndFlush(oldPromotion.getProducts());
            oldPromotion.getProducts().clear();
        } else {
            // Remove all existing associations
            if (oldPromotion.getProducts() != null) {
                List<Product> products = new ArrayList<>(oldPromotion.getProducts());
                products.forEach(product -> product.setPromotion(null));
                productRepository.saveAllAndFlush(products);
                oldPromotion.getProducts().clear();
            }

            // Associate new products
            if (request.getProductIds() != null) {
                List<Product> products = productRepository.findAllById(request.getProductIds());
                for (Product product : products) {
                    product.setPromotion(oldPromotion);
                }
                productRepository.saveAllAndFlush(products);
                oldPromotion.setProducts(products);
            }
        }

        Promotion response = promotionRepository.saveAndFlush(oldPromotion);
        return modelMapper.map(response, PromotionResponse.class);
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Promotion not found"));

        promotion.getProducts().forEach(p -> p.setPromotion(null));
        productRepository.saveAllAndFlush(promotion.getProducts());

        promotion.setProducts(null);
        promotionRepository.delete(promotion);
    }

    @Override
    @Transactional
    public void updateExpiredPromotions() {
        ZonedDateTime now = ZonedDateTime.now();
        List<Promotion> promotions = promotionRepository.findByEndDateBefore(now);
        if (!promotions.isEmpty()) {
            for (Promotion promotion : promotions) {
                promotion.setActive(false);
                for (Product product : promotion.getProducts()) {
                    product.setPromotion(null);
                }
                productRepository.saveAllAndFlush(promotion.getProducts());
            }
            promotionRepository.saveAllAndFlush(promotions);
        }

    }
}
