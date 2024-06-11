package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductAtrributeGroupRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeGroupResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttributeGroup;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.ExistDataException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductAttributeGroupRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductAttributeRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductAtrributeGroupService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductAtrributeGroupServiceImpl implements ProductAtrributeGroupService {

    private final ProductAttributeGroupRepository groupRepository;
    private final ProductAttributeRepository atrributeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductAtrributeGroupResponse> getAllProductAtrributeGroups() {
        return groupRepository.findAll().stream().map(g -> modelMapper.map(g, ProductAtrributeGroupResponse.class))
                .toList();
    }

    @Override
    public ProductAtrributeGroupResponse getProductAtrributeGroupById(Long id) {
        return modelMapper.map(
                groupRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("No Product Atrribute Group found.")),
                ProductAtrributeGroupResponse.class);
    }

    @Override
    public ProductAtrributeGroupResponse createProductAtrributeGroup(ProductAtrributeGroupRequest request) {
        Optional<ProductAttributeGroup> existGroup = groupRepository.findByName(request.getName());
        if (existGroup.isPresent()) {
            throw new ExistDataException("Product Atrribute Group existed.");
        }
        ProductAttributeGroup group = ProductAttributeGroup.builder()
                .name(request.getName())
                .build();
        return modelMapper.map(groupRepository.saveAndFlush(group), ProductAtrributeGroupResponse.class);
    }

    @Override
    public ProductAtrributeGroupResponse updateProductAtrributeGroup(ProductAtrributeGroupRequest request, Long id) {
        Optional<ProductAttributeGroup> existGroup = groupRepository.findByName(request.getName());
        if (existGroup.isPresent()) {
            throw new ExistDataException("Product Atrribute Group existed.");
        }
        ProductAttributeGroup oldGroup = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No Product Atrribute Group found."));
        oldGroup.setName(request.getName());

        return modelMapper.map(groupRepository.saveAndFlush(oldGroup), ProductAtrributeGroupResponse.class);
    }

    @Override
    public void deleteProductAtrributeGroup(Long id) {
        ProductAttributeGroup oldGroup = groupRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No Product Atrribute Group found."));
        ProductAttributeGroup defaultGroup = groupRepository.findByName("Default")
                .orElseThrow(() -> new DataNotFoundException("Default attribute group not found"));

        oldGroup.getProductAttributes().forEach(p -> p.setProductAttributeGroup(defaultGroup));
        atrributeRepository.saveAllAndFlush(oldGroup.getProductAttributes());

        groupRepository.delete(oldGroup);
    }

}
