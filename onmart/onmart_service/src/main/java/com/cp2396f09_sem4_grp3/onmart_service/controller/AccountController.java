package com.cp2396f09_sem4_grp3.onmart_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.AccountRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.ChangePasswordRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.AddressRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.AccountResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ChangePasswordResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.AddressResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.AccountService;
import com.cp2396f09_sem4_grp3.onmart_service.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final AddressService addressService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccountDetails(@AuthenticationPrincipal UserDetails user) {
        return accountService.getAccountInformation(user.getUsername());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse updateAccountDetails(@AuthenticationPrincipal UserDetails user,
            @RequestBody AccountRequest request) {
        return accountService.updateAccountInformation(user.getUsername(), request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.CREATED)
    public ChangePasswordResponse changeAccountPassword(@AuthenticationPrincipal UserDetails user,
            @RequestBody ChangePasswordRequest request) {
        return accountService.changeAccountPassword(request, user.getUsername());
    }

    @GetMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponse> getAllAddressesInUser(@AuthenticationPrincipal UserDetails user) {
        return addressService.getAllAddressesByUserEmail(user.getUsername());
    }

    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse createAddressInUser(@AuthenticationPrincipal UserDetails user,
            @RequestBody AddressRequest request) {
        return addressService.createAddressByUserEmail(user.getUsername(), request);
    }

    @PutMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse updateAddressById(@PathVariable Long id, @RequestBody AddressRequest request) {
        return addressService.updateAddressById(id, request);
    }

    @PutMapping("/addresses/{id}/default")
    @ResponseStatus(HttpStatus.OK)
    public void setAddressDefaultById(@PathVariable Long id) {
        addressService.setAddressDefaultById(id);
    }

    @GetMapping("/addresses/default")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponse getDefaultAddressByAccout(@AuthenticationPrincipal UserDetails user) {
        return addressService.getDafaultAddressByUserEmail(user.getUsername());
    }

    @DeleteMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        addressService.deleteAddressById(id, user.getUsername());
    }

}
