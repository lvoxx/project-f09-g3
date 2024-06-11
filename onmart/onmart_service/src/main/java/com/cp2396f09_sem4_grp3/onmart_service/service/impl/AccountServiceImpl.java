package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.AccountRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.ChangePasswordRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.AccountResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ChangePasswordResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.PasswordNotMatchingException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.AccountService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Value("${app.messages.user.exception.trashed-user}")
    private String exTrashedUser;

    private final UserService userService;
    private final UserRepository userRepository;
    private final org.modelmapper.ModelMapper modelMapper;

    @Override
    public AccountResponse getAccountInformation(String email) {
        return modelMapper.map(userService.findByEmail(email), AccountResponse.class);
    }

    @Override
    public AccountResponse updateAccountInformation(String email, AccountRequest request) {
        User oldUser = userService.findByEmail(email);

        oldUser.setFirstName(request.getFirstName());
        oldUser.setLastName(request.getLastName());
        oldUser.setPhone(request.getPhone());

        return modelMapper.map(userRepository.saveAndFlush(oldUser), AccountResponse.class);
    }

    @Override
    public ChangePasswordResponse changeAccountPassword(ChangePasswordRequest request, String email) {
        User user = userService.findByEmail(email);
        if (!userService.oldPasswordIsValid(user, request.getOldPassword())) {
            throw new PasswordNotMatchingException("Incorrect old password");
        }
        userService.changePassword(user, request.getNewPassword());
        return new ChangePasswordResponse(user.getEmail(), "Password changed successfully");
    }

}
