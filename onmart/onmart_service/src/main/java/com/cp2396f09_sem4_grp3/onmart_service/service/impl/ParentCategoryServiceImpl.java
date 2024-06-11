package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ParentCategoryRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ParentCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ParentCategory;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.ExistDataException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ImageUtils;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ChildCategoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ParentCategoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ParentCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentCategoryServiceImpl implements ParentCategoryService {

    @Value("${app.resources.image.category}")
    private String ctgImgPath;

    private final ParentCategoryRepository categoryRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ParentCategoryResponse createCategory(ParentCategoryRequest request) throws IOException {
        Optional<ParentCategory> existCategory = categoryRepository.findByName(request.getName());
        if (existCategory.isPresent()) {
            throw new ExistDataException("Category name " + request.getName() + " is existed");
        }
        MultipartFile image = request.getImage();
        ParentCategory category = ParentCategory.builder()
                .name(request.getName())
                .type(image.getContentType())
                .imageData(ImageUtils.compressImage(ImageUtils.resizeImage(image.getBytes(), 500, 500)))
                .imageName(image.getOriginalFilename())
                .build();
        return convertToDto(categoryRepository.saveAndFlush(category));
    }

    @Override
    public void deleteCategory(Long id) {
        ParentCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        ParentCategory defaultCategory = categoryRepository.findByName("Default")
                .orElseThrow(() -> new DataNotFoundException("Default category not found"));

        if (category.getChildCategory() != null) {
            category.getChildCategory().forEach(c -> c.setParentCategory(defaultCategory));
            childCategoryRepository.saveAllAndFlush(category.getChildCategory());
        }

        categoryRepository.delete(category);
    }

    @Override
    public List<ParentCategoryResponse> getAllCategories() {
        return this.categoryRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public ParentCategoryResponse getCategoryById(Long id) {
        return convertToDto(this.categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found.")));
    }

    @Override
    public ParentCategoryResponse updateCategory(ParentCategoryRequest request, Long id) throws IOException {
        ParentCategory oldCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found."));
        MultipartFile image = request.getImage();
        oldCategory.setName(request.getName());
        if (image != null) {
            oldCategory.setImageName(image.getOriginalFilename());
            oldCategory.setImageData(ImageUtils.compressImage(ImageUtils.resizeImage(image.getBytes(), 500, 500)));
            oldCategory.setType(image.getContentType());
        }
        return convertToDto(categoryRepository.saveAndFlush(oldCategory));
    }

    @Override
    public ImageResponse getCategoryImageById(Long id) {
        Optional<ParentCategory> image = categoryRepository.findById(id);
        if (!image.isPresent())
            throw new DataNotFoundException("No image with id " + id + " found");
        ImageResponse response = modelMapper.map(image.get(), ImageResponse.class);
        response.setImageData(ImageUtils.decompressImage(response.getImageData()));
        return response;
    }

    private ParentCategoryResponse convertToDto(ParentCategory category) {
        ParentCategoryResponse response = modelMapper.map(category, ParentCategoryResponse.class);
        if (category.getImageData() != null) {
            String imageUrl = this.ctgImgPath.replace("$id", category.getId().toString());
            response.setImageUrl(imageUrl);
        }
        return response;
    }
}
