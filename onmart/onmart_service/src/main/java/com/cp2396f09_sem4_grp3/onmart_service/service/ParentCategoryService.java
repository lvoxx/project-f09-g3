package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.io.IOException;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ParentCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ParentCategoryResponse;

public interface ParentCategoryService {

    List<ParentCategoryResponse> getAllCategories();

    ParentCategoryResponse getCategoryById(Long id);

    ImageResponse getCategoryImageById(Long id);

    ParentCategoryResponse createCategory(ParentCategoryRequest request) throws IOException;

    ParentCategoryResponse updateCategory(ParentCategoryRequest request, Long id) throws IOException;

    void deleteCategory(Long id);
}
