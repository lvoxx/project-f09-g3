package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ChildCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ChildCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/child-categories")
@RequiredArgsConstructor
public class ExChildCategoryController {

    private final ChildCategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChildCategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChildCategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
