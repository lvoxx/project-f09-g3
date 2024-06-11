package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ParentCategory;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {
    @Query("SELECT c FROM ParentCategory c WHERE c.name = ?1")
    Optional<ParentCategory> findByName(String name);

}
