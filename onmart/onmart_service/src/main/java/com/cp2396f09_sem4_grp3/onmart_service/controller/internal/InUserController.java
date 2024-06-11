package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UpdateUserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.UserRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.UserResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/users")
public class InUserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(request, id);
    }

    @PutMapping("/lock/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void lockUser(@PathVariable Long id) {
        userService.lockUserById(id);
    }

    @PutMapping("/unlock/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlockUser(@PathVariable Long id) {
        userService.unlockUserById(id);
    }
}
