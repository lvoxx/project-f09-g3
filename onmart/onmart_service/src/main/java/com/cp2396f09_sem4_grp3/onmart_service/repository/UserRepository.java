package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.ERole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        @Query("SELECT u FROM User u WHERE u.email = ?1")
        Optional<User> findByEmail(String email);

        @Modifying
        @Query("UPDATE User u SET u.isTrashed = true WHERE u.id = :id")
        void trashedUserById(@Param("id") Long id);

        @Modifying
        @Query("UPDATE User u SET u.isTrashed = false WHERE u.id = :id")
        void untrashedUserById(@Param("id") Long id);

        @Modifying
        @Query("UPDATE User u SET u.loyatyPoints = u.loyatyPoints + :points WHERE u.id = :id")
        void updateLoyaltyPoints(@Param("points") BigDecimal points, @Param("id") Long id);

        // Registered Users
        @Query("SELECT u FROM User u WHERE (u.createdOn BETWEEN :start AND :end) ORDER BY u.id ASC")
        List<User> findRegisterdByCreatedOnBetweenSortedAsc(@Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT u FROM User u WHERE (u.createdOn BETWEEN :start AND :end) ORDER BY u.id DESC")
        List<User> findRegisterdByCreatedOnBetweenSortedDesc(@Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Disabled Users
        @Query("SELECT u FROM User u WHERE (u.lastModifiedOn BETWEEN :start AND :end) AND u.isTrashed = true ORDER BY u.id ASC")
        List<User> findDisabledByTrashedBetweenSortedAsc(@Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        @Query("SELECT u FROM User u WHERE (u.lastModifiedOn BETWEEN :start AND :end) AND u.isTrashed = true ORDER BY u.id ASC")
        List<User> findDisabledByTrashedBetweenSortedDesc(@Param("start") ZonedDateTime start,
                        @Param("end") ZonedDateTime end);

        // Count
        @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
        Long countByRole(@Param("role") ERole role);
}
