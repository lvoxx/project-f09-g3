package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("SELECT s FROM Promotion s WHERE s.name = ?1")
    Optional<Promotion> findByName(String name);

    List<Promotion> findByEndDateBefore(ZonedDateTime dateTime);
}
