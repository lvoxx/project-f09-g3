package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeGroupResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/product-atrribute-groups")
@RequiredArgsConstructor
public class ExProductAtrributeGroupController {

    private final ProductAtrributeGroupService groupService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductAtrributeGroupResponse> getProductAtrributeGroup() {
        return groupService.getAllProductAtrributeGroups();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductAtrributeGroupResponse getProductAtrributeGroupById(@PathVariable Long id) {
        return groupService.getProductAtrributeGroupById(id);
    }

}
