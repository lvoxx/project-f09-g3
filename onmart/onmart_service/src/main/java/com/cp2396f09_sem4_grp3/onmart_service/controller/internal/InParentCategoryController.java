package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ParentCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ParentCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ParentCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/parent-categories")
@RequiredArgsConstructor
public class InParentCategoryController {

    private final ParentCategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParentCategoryResponse createCategory(@ModelAttribute ParentCategoryRequest request)
            throws IOException {
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParentCategoryResponse updateCategory(@ModelAttribute ParentCategoryRequest request,
            @PathVariable("id") Long id) throws IOException {
        return categoryService.updateCategory(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
