package com.cp2396f09_sem4_grp3.onmart_service.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PublicProductResponse;

public interface ProductService {
    Page<PublicProductResponse> getAllPublicProducts(Integer pageNo, String sortBy);

    Page<PublicProductResponse> getAllPublicProductsByName(String name, Integer pageNo, String sortBy);

    Page<PublicProductResponse> getAllPublicProductsByParentCategoryName(String parentCategoryName, Integer pageNo, String sortBy);

    PublicProductResponse getProductById(Long id);

    ImageResponse getProductImageById(Long id);

    PublicProductResponse createProduct(ProductRequest request) throws IOException;

    PublicProductResponse updateProduct(ProductRequest request, Long id);

    void deleteProduct(Long id);

    void increasePurchaseByOne(Long id);

    void importProduct(int importQuantity, Long id);

    void exportProduct(int exportQuantity, Long id);
}
