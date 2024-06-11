package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN FETCH p.category cc JOIN FETCH cc.parentCategory pc WHERE pc.name = :parentCategoryName")
    Page<Product> findAllByParentCategoryName(@Param("parentCategoryName") String parentCategoryName,
            Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.purchased = p.purchased + 1 WHERE p.id = :id")
    void increasePurchasesById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Product p SET p.inSellQuantity = p.inSellQuantity + :exportP WHERE p.id = :id")
    void updateInSellQuantityById(@Param("id") Long id, @Param("exportP") int exportP);

    @Modifying
    @Query("UPDATE Product p SET p.inStockQuantity = p.inStockQuantity + :importP WHERE p.id = :id")
    void updateInStockQuantityById(@Param("id") Long id, @Param("importP") int importP);

    // Sum Products in stock and in sell quantity
    @Query("SELECT SUM(p.inStockQuantity) FROM Product p")
    Long sumInStock();

    @Query("SELECT SUM(p.inSellQuantity) FROM Product p")
    Long sumInSell();

    // Find Product may be out of stock if <= lowStock
    @Query("SELECT p FROM Product p WHERE p.inStockQuantity <= :lowStock")
    List<Product> findProductsWithLowStock(@Param("lowStock") int lowStock);
}
