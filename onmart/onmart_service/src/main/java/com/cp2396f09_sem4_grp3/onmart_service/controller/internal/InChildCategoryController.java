package com.cp2396f09_sem4_grp3.onmart_service.controller.internal;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ChildCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ChildCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_service.service.ChildCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private/child-categories")
@RequiredArgsConstructor
public class InChildCategoryController {
    private final ChildCategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChildCategoryResponse createCategory(@RequestBody ChildCategoryRequest request)
            throws IOException {
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChildCategoryResponse updateCategory(@RequestBody ChildCategoryRequest request,
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
