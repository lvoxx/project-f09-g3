package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ChildCategory;

@Repository
public interface ChildCategoryRepository extends JpaRepository<ChildCategory, Long> {
    @Query("SELECT c FROM ChildCategory c WHERE c.name = ?1")
    Optional<ChildCategory> findByName(String name);
}
