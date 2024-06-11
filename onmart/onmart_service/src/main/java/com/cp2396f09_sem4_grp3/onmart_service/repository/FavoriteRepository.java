package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("SELECT f FROM Favorite f JOIN FETCH f.user u  WHERE u.email = :email")
    List<Favorite> findAllByUserEmail(@Param("email") String userEmail);

    @Query("SELECT f FROM Favorite f JOIN FETCH f.user u  WHERE u.email = :email AND f.id = :id")
    List<Favorite> findAllByUserEmailAndId(@Param("email") String userEmail, @Param("id") Long id);
}
