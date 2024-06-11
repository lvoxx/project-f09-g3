package com.cp2396f09_sem4_grp3.onmart_service.service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.AccountRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.ChangePasswordRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.AccountResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ChangePasswordResponse;

public interface AccountService {

    AccountResponse getAccountInformation(String email);

    AccountResponse updateAccountInformation(String email, AccountRequest request);

    // Change password for secure account
    ChangePasswordResponse changeAccountPassword(ChangePasswordRequest request, String email);
}
