package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductReviewRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductReviewResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductReview;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductReviewRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductReviewService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductReviewResponse> getAllProductReviews(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 20, Sort.by("id"));
        return reviewRepository.findAll(paging)
                .map(r -> modelMapper.map(r, ProductReviewResponse.class));
    }

    @Override
    public Page<ProductReviewResponse> getAllReviewByProductId(Integer pageNo, Long productId) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        return reviewRepository.findAllByProductId(productId, paging)
                .map(r -> modelMapper.map(r, ProductReviewResponse.class));
    }

    @Override
    public List<ProductReviewResponse> createReviewsByCustomer(List<ProductReviewRequest> requests, String email) {
        User customer = userService.findByEmail(email);
        List<ProductReview> responses = requests.stream().map(r -> {
            Product product = productRepository.findById(r.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("No product found."));
            return ProductReview.builder()
                    .product(product)
                    .user(customer)
                    .review(r.getReview())
                    .build();
        }).toList();
        return reviewRepository.saveAllAndFlush(responses).stream()
                .map(r -> modelMapper.map(r, ProductReviewResponse.class)).toList();
    }

    @Override
    public void deleteReviewById(Long id) {
        ProductReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No product found."));
        review.getProduct().getProductReviews().remove(review);
        productRepository.saveAndFlush(review.getProduct());

        review.getUser().getProductReviews().remove(review);
        userRepository.saveAndFlush(review.getUser());

        reviewRepository.deleteById(id);
    }
}
