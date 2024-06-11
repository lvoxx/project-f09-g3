package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttributeGroup;

@Repository
public interface ProductAttributeGroupRepository extends JpaRepository<ProductAttributeGroup, Long> {
    @Query("SELECT g FROM ProductAttributeGroup g WHERE g.name = ?1")
    Optional<ProductAttributeGroup> findByName(String name);

}
