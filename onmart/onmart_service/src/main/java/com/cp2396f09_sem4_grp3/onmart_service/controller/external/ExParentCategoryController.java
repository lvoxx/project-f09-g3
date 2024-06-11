package com.cp2396f09_sem4_grp3.onmart_service.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ParentCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ImageUtils;
import com.cp2396f09_sem4_grp3.onmart_service.service.ParentCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/parent-categories")
@RequiredArgsConstructor
public class ExParentCategoryController {

    private final ParentCategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParentCategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParentCategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable Long id) {
        ImageResponse response = categoryService.getCategoryImageById(id);

        return new ResponseEntity<>(response.getImageData(), ImageUtils.imagHeaders(response.getType()), HttpStatus.OK);
    }

}
