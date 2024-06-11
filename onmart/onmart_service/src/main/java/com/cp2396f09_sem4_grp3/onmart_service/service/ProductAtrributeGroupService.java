package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeGroupRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeGroupResponse;

public interface ProductAtrributeGroupService {
    List<ProductAtrributeGroupResponse> getAllProductAtrributeGroups();

    ProductAtrributeGroupResponse getProductAtrributeGroupById(Long id);

    ProductAtrributeGroupResponse createProductAtrributeGroup(ProductAtrributeGroupRequest request);

    ProductAtrributeGroupResponse updateProductAtrributeGroup(ProductAtrributeGroupRequest request, Long id);

    void deleteProductAtrributeGroup(Long id);
}
