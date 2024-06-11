package com.cp2396f09_sem4_grp3.onmart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp2396f09_sem4_grp3.onmart_common.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String passwordResetToken);
}
