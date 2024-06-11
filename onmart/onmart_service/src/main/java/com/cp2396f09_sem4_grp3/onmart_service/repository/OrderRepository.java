package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Order;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
        @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
        Page<Order> findOrderByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

        @Query("SELECT o FROM Order o WHERE o.state = :state AND o.customerId = :customerId")
        Page<Order> findOrderByStateAndCustomerId(@Param("state") EOrderState state,
                        @Param("customerId") Long customerId,
                        Pageable pageable);

        @Query("SELECT o FROM Order o WHERE o.state = :state")
        Page<Order> findOrderByState(@Param("state") EOrderState state,
                        Pageable pageable);

        @Modifying
        @Query("UPDATE Order o SET o.totalPrice = :totalPrice WHERE o.id = :id")
        void updateTotalPriceById(@Param("totalPrice") BigDecimal totalPrice, @Param("id") Long id);

        @Modifying
        @Query("UPDATE Order o SET o.state = :state WHERE o.id = :id")
        void updateOrderStateById(@Param("state") EOrderState state, @Param("id") Long id);

        @Modifying
        @Query("UPDATE Order o SET o.state = 'CANCELED', o.rejectReason = :rejectReason WHERE o.id = :id")
        void cancelOrderById(@Param("rejectReason") String rejectReason, @Param("id") Long id);

        // Dashboard
        @Query("SELECT o FROM Order o WHERE (o.createdOn BETWEEN :start AND :end) ORDER BY o.id ASC")
        List<Order> findOrderByCreatedOnBetweenSortedAsc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT o FROM Order o WHERE (o.createdOn BETWEEN :start AND :end) ORDER BY o.id DESC")
        List<Order> findOrderByCreatedOnBetweenSortedDesc(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Find Revenue and loyalty points callback Sum date betweem
        @Query("SELECT SUM(o.totalPrice - o.deliveryFee) FROM Order o WHERE o.createdOn BETWEEN :start AND :end")
        BigDecimal findRevenueByCreatedOnBetwwen(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT SUM(o.loyaltyPointsPay) FROM Order o WHERE (o.createdOn BETWEEN :start AND :end) AND o.state = com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EOrderState.CANCELED")
        BigDecimal findLoyaltyPointsCallbackByCreatedOnBetween(
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Count by state and date between
        @Query("SELECT COUNT(o) FROM Order o WHERE o.state = :state AND (o.createdOn BETWEEN :start AND :end)")
        Long countByStateBetweenDate(
                        @Param("state") EOrderState state,
                        @Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);
}
