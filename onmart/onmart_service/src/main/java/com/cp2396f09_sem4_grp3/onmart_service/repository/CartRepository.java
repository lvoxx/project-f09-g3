package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.user u  WHERE u.email = :email")
    List<Cart> findAllByUserEmail(@Param("email") String userEmail);

    @Query("SELECT c FROM Cart c JOIN FETCH c.user u  WHERE u.email = :email AND c.id = :id")
    List<Cart> findAllByUserEmailAndId(@Param("email") String userEmail, @Param("id") Long id);

    @Query("SELECT c FROM Cart c JOIN FETCH c.user u JOIN FETCH c.product p WHERE u.id = :userId AND p.id = :productId")
    Optional<Cart> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = c.quantity + 1 WHERE c.id = :id")
    void increaseCartsById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = c.quantity - 1 WHERE c.id = :id")
    void decreaseCartsById(@Param("id") Long id);
}
