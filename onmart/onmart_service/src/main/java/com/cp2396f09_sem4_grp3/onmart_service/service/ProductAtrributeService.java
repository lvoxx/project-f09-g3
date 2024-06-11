package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.util.List;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeResponse;

public interface ProductAtrributeService {
    List<ProductAtrributeResponse> getAllProductAtrributes();

    ProductAtrributeResponse getProductAtrributeById(Long id);

    ProductAtrributeResponse createProductAtrribute(ProductAtrributeRequest request);

    ProductAtrributeResponse updateProductAtrribute(ProductAtrributeRequest request, Long id);

    void deleteProductAtrribute(Long id);
}
