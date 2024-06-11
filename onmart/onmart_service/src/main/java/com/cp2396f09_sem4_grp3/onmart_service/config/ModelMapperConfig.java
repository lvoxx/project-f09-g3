package com.cp2396f09_sem4_grp3.onmart_service.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.CartResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ChildCategoryResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.FavoriteResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductAtrributeResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ProductReviewResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PublicProductResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Cart;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ChildCategory;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Favorite;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttribute;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductReview;

@Configuration
public class ModelMapperConfig {
    @Value("${app.resources.image.product}")
    private String procductImgPath;

    @Bean
    public ModelMapper modelMapper() {
        // Config
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Custom converter for Product to PublicProductResponse
        Converter<Product, PublicProductResponse> productToProductDtoConverter = context -> {
            Product source = context.getSource();
            PublicProductResponse destination = context.getDestination();
            if (!source.getImages().isEmpty()) {
                List<String> imageUrls = new ArrayList<>();
                source.getImages().stream().forEach(i -> imageUrls
                        .add(procductImgPath.replace("$id", String.valueOf(i.getId()))));
                destination.setImagesUrl(imageUrls);
            }
            // NOTE: category reponse
            if (source.getCategory() != null) {
                destination.getCategory().setParentId(source.getCategory().getParentCategory().getId());
                destination.getCategory().setParentName(source.getCategory().getParentCategory().getName());
            }
            // NOTE: product attribute reponse
            if (source.getProductAttributes() != null) {
                List<ProductAttribute> srcAttris = new ArrayList<>(source.getProductAttributes());
                List<ProductAtrributeResponse> desAttris = new ArrayList<>(destination.getProductAttributes());
                IntStream.range(0, srcAttris.size()).forEach(i -> {
                    desAttris.get(i).setGroupId(srcAttris.get(i).getProductAttributeGroup().getId());
                    desAttris.get(i).setGroupName(srcAttris.get(i).getProductAttributeGroup().getName());
                });
                destination.setProductAttributes(new HashSet<>(desAttris));
            }

            return destination;
        };

        // Custom converter for ChildCategory to ChildCategoryResponse
        Converter<ChildCategory, ChildCategoryResponse> childCategoryToChildCategoryResponse = context -> {
            ChildCategory source = context.getSource();
            ChildCategoryResponse destination = context.getDestination();
            if (source.getParentCategory() != null) {
                destination.setParentId(source.getParentCategory().getId());
                destination.setParentName(source.getParentCategory().getName());
            }
            return destination;
        };

        // Custom converter for Favorite to FavoriteResponse
        Converter<Favorite, FavoriteResponse> favoriteToFavoriteResponse = context -> {
            Favorite source = context.getSource();
            FavoriteResponse destination = context.getDestination();
            if (source.getProduct() != null) {
                destination.setProductId(source.getProduct().getId());
                destination.setProductName(source.getProduct().getName());
            }
            return destination;
        };

        // Custom converter for ProductAtrribute to ProductAtrributeResponse
        Converter<ProductAttribute, ProductAtrributeResponse> productAtrributeToProductAtrributeResponse = context -> {
            ProductAttribute source = context.getSource();
            ProductAtrributeResponse destination = context.getDestination();
            if (source.getProductAttributeGroup() != null) {
                destination.setGroupId(source.getProductAttributeGroup().getId());
                destination.setGroupName(source.getProductAttributeGroup().getName());
            }
            return destination;
        };

        // Custom converter for Cart to CartResponse
        Converter<Cart, CartResponse> cartToCartResponse = context -> {
            Cart source = context.getSource();
            CartResponse destination = context.getDestination();
            if (source.getProduct() != null) {
                destination.setProductId(source.getProduct().getId());
                destination.setProductName(source.getProduct().getName());
            }
            return destination;
        };

        // Custom converter for ProductReview to ProductReviewResponse
        Converter<ProductReview, ProductReviewResponse> productReviewToProductReviewtResponse = context -> {
            ProductReview source = context.getSource();
            ProductReviewResponse destination = context.getDestination();
            if (source.getProduct() != null) {
                destination.setProductId(source.getProduct().getId());
                destination.setProductName(source.getProduct().getName());
            }
            if (source.getUser() != null) {
                destination.setCustomerId(source.getUser().getId());
                destination.setCustomerName(source.getUser().getFirstName() + " " + source.getUser().getLastName());
            }
            return destination;
        };

        // Define a type map and set the converter
        // Product mapping
        modelMapper.createTypeMap(Product.class, PublicProductResponse.class)
                .setPostConverter(productToProductDtoConverter);
        // ChildCategory mapping
        modelMapper.createTypeMap(ChildCategory.class, ChildCategoryResponse.class)
                .setPostConverter(childCategoryToChildCategoryResponse);
        // ProductAttribute mapping
        modelMapper.createTypeMap(ProductAttribute.class, ProductAtrributeResponse.class)
                .setPostConverter(productAtrributeToProductAtrributeResponse);
        // Favorite mapping
        modelMapper.createTypeMap(Favorite.class, FavoriteResponse.class)
                .setPostConverter(favoriteToFavoriteResponse);
        // Cart mapping
        modelMapper.createTypeMap(Cart.class, CartResponse.class)
                .setPostConverter(cartToCartResponse);
        // ProductReview mapping
        modelMapper.createTypeMap(ProductReview.class, ProductReviewResponse.class)
                .setPostConverter(productReviewToProductReviewtResponse);

        return modelMapper;
    }
}
