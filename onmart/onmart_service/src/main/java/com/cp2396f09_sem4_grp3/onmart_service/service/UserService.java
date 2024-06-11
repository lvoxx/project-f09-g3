package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.RegistrationRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UpdateUserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.UserResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_common.entities.VerificationToken;

public interface UserService {

    // WARNING: Dont use this, for Register and Login Service, don't modify
    // too
    List<User> getUsers();

    User findById(Long id);

    // Usage: For Admin

    Page<UserResponse> listAllUsers(Integer pageNo, String sortBy);

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(UpdateUserRequest request, Long id);

    void lockUserById(Long id);

    void unlockUserById(Long id);

    // For Order:

    void addLoyaltyPoints(BigDecimal points, Long id);

    // Usage: Registry a new customer
    User addUserByGuest(RegistrationRequest request);

    User findByEmail(String email);

    // Usage: Verify email registration
    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);

    // Usage: Jwt filter
    UserDetailsService userDetailsFromUser();

    // Usage: Reset password
    void changePassword(User theUser, String newPassword);

    String validatePasswordResetToken(String token);

    User findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);

    boolean oldPasswordIsValid(User user, String oldPassword);
}
