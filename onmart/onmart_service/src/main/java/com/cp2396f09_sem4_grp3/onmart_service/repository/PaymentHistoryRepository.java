package com.cp2396f09_sem4_grp3.onmart_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.PaymentHistory;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    @Query("SELECT p FROM PaymentHistory p WHERE p.customerId = :customerId")
    Page<PaymentHistory> findPaymentHistoryByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
}
