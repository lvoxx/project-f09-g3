package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.io.IOException;
import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ChildCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ChildCategoryResponse;

public interface ChildCategoryService {
    List<ChildCategoryResponse> getAllCategories();

    ChildCategoryResponse getCategoryById(Long id);

    ChildCategoryResponse createCategory(ChildCategoryRequest request) throws IOException;

    ChildCategoryResponse updateCategory(ChildCategoryRequest request, Long id) throws IOException;

    void deleteCategory(Long id);
}
