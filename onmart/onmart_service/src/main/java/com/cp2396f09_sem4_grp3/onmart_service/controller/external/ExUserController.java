package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.UserResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/users")
public class ExUserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponse> getUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return userService.listAllUsers(page, sort);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

}
