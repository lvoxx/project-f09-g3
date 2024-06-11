package com.cp2396f09_sem4_grp3.onmart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = :isDefault WHERE a.id = :id")
    void setAddressDefaultById(@Param("id") Long id, @Param("isDefault") boolean isDefault);

    @Query("SELECT a FROM Address a JOIN FETCH a.user u WHERE a.isDefault = true AND u.id = :userId")
    Optional<Address> findDefaultAddressByUserId(@Param("userId") Long userId);
}
