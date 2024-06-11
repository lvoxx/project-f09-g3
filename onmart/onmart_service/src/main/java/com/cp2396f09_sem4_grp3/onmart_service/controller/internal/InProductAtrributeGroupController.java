package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeGroupRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeGroupResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/product-atrribute-groups")
@RequiredArgsConstructor
public class InProductAtrributeGroupController {

    private final ProductAtrributeGroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAtrributeGroupResponse createProductAtrributeGroup(
            @RequestBody ProductAtrributeGroupRequest request) {
        return groupService.createProductAtrributeGroup(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductAtrributeGroupResponse updateProductAtrributeGroup(@PathVariable Long id,
            @RequestBody ProductAtrributeGroupRequest request) {
        return groupService.updateProductAtrributeGroup(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softProductAtrributeGroup(@PathVariable Long id) {
        groupService.deleteProductAtrributeGroup(id);
    }
}
