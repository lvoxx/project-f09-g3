package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ChildCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ChildCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ChildCategory;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ParentCategory;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.ExistDataException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ChildCategoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ParentCategoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ChildCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildCategoryServiceImpl implements ChildCategoryService {

        @Value("${app.resources.image.category}")
        private String ctgImgPath;

        private final ParentCategoryRepository parentRepository;
        private final ChildCategoryRepository childRepository;
        private final ProductRepository productRepository;
        private final ModelMapper modelMapper;

        @Override
        public List<ChildCategoryResponse> getAllCategories() {
                return this.childRepository.findAll().stream()
                                .map(c -> modelMapper.map(c, ChildCategoryResponse.class))
                                .toList();
        }

        @Override
        public ChildCategoryResponse getCategoryById(Long id) {
                return modelMapper.map(
                                this.childRepository.findById(id)
                                                .orElseThrow(() -> new DataNotFoundException("Category not found")),
                                ChildCategoryResponse.class);
        }

        @Override
        public ChildCategoryResponse createCategory(ChildCategoryRequest request) throws IOException {
                Optional<ChildCategory> existChildCategory = childRepository.findByName(request.getName());
                if (existChildCategory.isPresent()) {
                        throw new ExistDataException("Category name " + request.getName() + " is existed");
                }
                ParentCategory parentCategory = parentRepository.findById(request.getParentId())
                                .orElseThrow(() -> new DataNotFoundException("Parent category not found"));

                ChildCategory childCategory = ChildCategory.builder()
                                .name(request.getName())
                                .parentCategory(parentCategory)
                                .build();

                return modelMapper.map(childRepository.saveAndFlush(childCategory), ChildCategoryResponse.class);
        }

        @Override
        public ChildCategoryResponse updateCategory(ChildCategoryRequest request, Long id) throws IOException {
                ChildCategory oldChildCategory = childRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Category not found"));
                ParentCategory parentCategory = parentRepository.findById(request.getParentId())
                                .orElseThrow(() -> new DataNotFoundException("Parent category not found"));

                oldChildCategory.setName(request.getName());
                oldChildCategory.setParentCategory(parentCategory);

                return modelMapper.map(childRepository.saveAndFlush(oldChildCategory), ChildCategoryResponse.class);
        }

        @Override
        public void deleteCategory(Long id) {
                ChildCategory oldChildCategory = childRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Category not found"));

                ChildCategory defaultCategory = childRepository.findByName("Default")
                                .orElseThrow(() -> new DataNotFoundException("Default category not found"));

                if (oldChildCategory.getProducts() != null) {
                        oldChildCategory.getProducts().forEach(prod -> prod.setCategory(defaultCategory));
                        productRepository.saveAllAndFlush(oldChildCategory.getProducts());
                }
                if (oldChildCategory.getParentCategory() != null) {
                        oldChildCategory.getParentCategory().getChildCategory().remove(oldChildCategory);
                        parentRepository.saveAndFlush(oldChildCategory.getParentCategory());
                }

                childRepository.delete(oldChildCategory);
        }

}
