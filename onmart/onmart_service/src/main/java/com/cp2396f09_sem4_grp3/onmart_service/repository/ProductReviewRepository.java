package com.cp2396f09_sem4_grp3.onmart_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    @Query("SELECT r FROM ProductReview r JOIN FETCH r.product p WHERE p.id = :productId")
    Page<ProductReview> findAllByProductId(@Param("productId") Long productId,
            Pageable pageable);
}
