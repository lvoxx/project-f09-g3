package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttribute;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttributeGroup;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductAttributeGroupRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductAttributeRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductAtrributeServiceImpl implements ProductAtrributeService {
    private final ProductAttributeGroupRepository groupRepository;
    private final ProductAttributeRepository atrributeRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductAtrributeResponse> getAllProductAtrributes() {
        return atrributeRepository.findAll().stream().map(p -> modelMapper.map(p, ProductAtrributeResponse.class))
                .toList();
    }

    @Override
    public ProductAtrributeResponse getProductAtrributeById(Long id) {
        return modelMapper.map(
                atrributeRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("No Product Atrribute found.")),
                ProductAtrributeResponse.class);
    }

    @Override
    public ProductAtrributeResponse createProductAtrribute(ProductAtrributeRequest request) {
        ProductAttributeGroup existAttributeGroup = groupRepository.findByName(request.getProductAttributeGroup())
                .orElseThrow(() -> new DataNotFoundException("No parent Product Atrribute Group found."));
        ProductAttribute productAttribute = ProductAttribute.builder()
                .name(request.getName())
                .priceDifference(request.getPriceDifference())
                .productAttributeGroup(existAttributeGroup)
                .build();
        return modelMapper.map(atrributeRepository.saveAndFlush(productAttribute), ProductAtrributeResponse.class);
    }

    @Override
    public ProductAtrributeResponse updateProductAtrribute(ProductAtrributeRequest request, Long id) {
        ProductAttribute oldAttribute = atrributeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No Product Atrribute found."));
        ProductAttributeGroup existAttributeGroup = groupRepository.findByName(request.getProductAttributeGroup())
                .orElseThrow(() -> new DataNotFoundException("No parent Product Atrribute Group found."));

        oldAttribute.setName(request.getName());
        oldAttribute.setPriceDifference(request.getPriceDifference());
        oldAttribute.setProductAttributeGroup(existAttributeGroup);

        return modelMapper.map(atrributeRepository.saveAndFlush(oldAttribute), ProductAtrributeResponse.class);
    }

    @Override
    public void deleteProductAtrribute(Long id) {
        ProductAttribute oldAttribute = atrributeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No Product Atrribute found."));

        oldAttribute.getProducts().forEach(p -> {
            Set<ProductAttribute> oldSet = p.getProductAttributes();
            oldSet.remove(oldAttribute);
            p.setProductAttributes(oldSet);
        });
        productRepository.saveAllAndFlush(oldAttribute.getProducts());

        oldAttribute.getProductAttributeGroup().getProductAttributes().remove(oldAttribute);
        groupRepository.saveAndFlush(oldAttribute.getProductAttributeGroup());

        atrributeRepository.delete(oldAttribute);
    }

}
