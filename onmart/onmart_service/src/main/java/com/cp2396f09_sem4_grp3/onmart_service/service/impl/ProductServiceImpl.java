package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ProductRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.ImageResponse;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.PublicProductResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ChildCategory;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Product;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductAttribute;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ProductImage;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Supplier;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.utils.ImageUtils;
import com.cp2396f09_sem4_grp3.onmart_service.repository.CartRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ChildCategoryRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.FavoriteRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductAttributeRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductImageRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ProductReviewRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.PromotionRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.SupplierRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        @Value("${app.jpa.max-items-in-page}")
        private int maxItems;

        private final ProductRepository productRepository;
        private final ProductReviewRepository productReviewRepository;
        private final PromotionRepository promotionRepository;
        private final ProductImageRepository imageRepository;
        private final ProductAttributeRepository attributeRepository;
        private final SupplierRepository supplierRepository;
        private final FavoriteRepository favoriteRepository;
        private final CartRepository cartRepository;
        private final ChildCategoryRepository categoryRepository;
        private final ModelMapper modelMapper;

        @Override
        public Page<PublicProductResponse> getAllPublicProducts(Integer pageNo, String sortBy) {
                Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
                return productRepository.findAll(paging)
                                .map(supplier -> modelMapper.map(supplier, PublicProductResponse.class));
        }

        @Override
        public Page<PublicProductResponse> getAllPublicProductsByName(String name, Integer pageNo, String sortBy) {
                Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
                return productRepository.findAllByName(name, paging)
                                .map(supplier -> modelMapper.map(supplier, PublicProductResponse.class));
        }

        @Override
        public Page<PublicProductResponse> getAllPublicProductsByParentCategoryName(String parentCategoryName,
                        Integer pageNo, String sortBy) {
                Pageable paging = PageRequest.of(pageNo, maxItems, Sort.by(sortBy));
                return productRepository.findAllByParentCategoryName(parentCategoryName, paging)
                                .map(supplier -> modelMapper.map(supplier, PublicProductResponse.class));
        }

        @Override
        public PublicProductResponse getProductById(Long id) {
                return modelMapper.map(
                                productRepository.findById(id)
                                                .orElseThrow(() -> new DataNotFoundException("No product found.")),
                                PublicProductResponse.class);
        }

        @Override
        public ImageResponse getProductImageById(Long id) {
                ProductImage image = imageRepository.findImageById(id)
                                .orElseThrow(() -> new DataNotFoundException("Image not found."));
                ImageResponse response = modelMapper.map(image, ImageResponse.class);
                response.setImageData(ImageUtils.decompressImage(response.getImageData()));
                return response;
        }

        // Nullable: suppliers, product attributes
        @Override
        public PublicProductResponse createProduct(ProductRequest request) throws IOException {
                // Add Images
                List<MultipartFile> images = request.getImages();
                List<ProductImage> productImages = new ArrayList<>();
                images.forEach(i -> {
                        try {
                                productImages.add(ProductImage.builder()
                                                .name(i.getOriginalFilename())
                                                .imageData(ImageUtils.compressImage(
                                                                ImageUtils.resizeImage(i.getBytes(), 1000, 1000)))
                                                .type(i.getContentType())
                                                .build());
                        } catch (IOException e) {
                        }
                });
                // Put data to product
                Supplier supplier = null;
                if (request.getSupplier_id() != null) {
                        supplier = supplierRepository.findById(request.getSupplier_id())
                                        .orElseThrow(() -> new DataNotFoundException("No supplier found."));
                }
                ChildCategory childCategory = categoryRepository.findById(request.getCategory_id())
                                .orElseThrow(() -> new DataNotFoundException("No category found."));
                Product product = Product.builder()
                                .name(request.getName())
                                .shortName(safeSubstring(request.getName(), 0, 30))
                                .importPrice(request.getImport_price())
                                .sellPrice(request.getSell_price())
                                .description(request.getDescription())
                                .shortDescription(safeSubstring(request.getDescription(), 0, 400))
                                .isPublic(request.getIs_published().equals("true"))
                                .isFeatured(request.getIs_featured().equals("true"))
                                // Set Category
                                .category(childCategory)
                                .build();
                // Set Supplier
                if (supplier != null) {
                        product.setSupplierId(supplier.getId());
                        product.setSupplierName(supplier.getSupplierName());
                } else {
                        product.setSupplierId(0L);
                        product.setSupplierName("No Supplier");
                }

                // Set Product Atrribute
                if (request.getProduct_attribute_ids() != null) {
                        Set<ProductAttribute> attributes = attributeRepository
                                        .findAllById(request.getProduct_attribute_ids())
                                        .stream()
                                        .collect(Collectors.toSet());
                        product.setProductAttributes(attributes);
                }
                Product response = productRepository.saveAndFlush(product);

                productImages.forEach(p -> p.setProductImages(response));
                imageRepository.saveAllAndFlush(productImages);
                return modelMapper.map(response, PublicProductResponse.class);
        }

        // TODO:
        // Update product inf
        // Update category
        // Update supplier
        // Update product attributes
        // Update images
        @Override
        public PublicProductResponse updateProduct(ProductRequest request, Long id) {
                Product oldProduct = productRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("No product found."));

                // UPDATE PRODUCT INF
                oldProduct.setName(request.getName());
                oldProduct.setShortName(safeSubstring(request.getName(), 0, 30));
                oldProduct.setImportPrice(request.getImport_price());
                oldProduct.setSellPrice(request.getSell_price());
                oldProduct.setDescription(request.getDescription());
                oldProduct.setShortDescription(safeSubstring(request.getDescription(), 0, 400));
                oldProduct.setFeatured(request.getIs_featured().equals("true"));
                oldProduct.setPublic(request.getIs_published().equals("true"));

                // UPDATE SUPPLIER
                if (request.getSupplier_id() != null && !request.getSupplier_id().equals(oldProduct.getSupplierId())) {
                        Supplier supplier = supplierRepository.findById(request.getSupplier_id())
                                        .orElseThrow(() -> new DataNotFoundException("No supplier found."));
                        oldProduct.setSupplierId(supplier.getId());
                        oldProduct.setSupplierName(supplier.getSupplierName());
                }

                // UPDATE CATEGORY
                if (!request.getCategory_id().equals(oldProduct.getCategory().getId())) {
                        ChildCategory category = categoryRepository.findById(request.getCategory_id())
                                        .orElseThrow(() -> new DataNotFoundException("No category found."));
                        oldProduct.setCategory(category);
                }

                // UPDATE IMAGES
                // -> Delete all image in one
                imageRepository.deleteAllByIdInBatch(oldProduct.getImages().stream()
                                .map(ProductImage::getId)
                                .collect(Collectors.toList()));
                // -> Update all new images
                List<MultipartFile> images = request.getImages();
                List<ProductImage> productImages = new ArrayList<>();
                images.forEach(i -> {
                        try {
                                productImages.add(ProductImage.builder()
                                                .name(i.getOriginalFilename())
                                                .imageData(ImageUtils.compressImage(
                                                                ImageUtils.resizeImage(i.getBytes(), 1000, 1000)))
                                                .type(i.getContentType())
                                                .build());
                        } catch (IOException e) {
                        }
                });

                // UPDATE PRODUCT ATTRIBUTES
                oldProduct.setProductAttributes(null);
                if (request.getProduct_attribute_ids() != null) {
                        Set<ProductAttribute> attributes = attributeRepository
                                        .findAllById(request.getProduct_attribute_ids())
                                        .stream()
                                        .collect(Collectors.toSet());
                        oldProduct.setProductAttributes(attributes);
                }

                // UPDATE DATA
                Product response = productRepository.saveAndFlush(oldProduct);

                // -> Set reference id
                productImages.forEach(p -> p.setProductImages(response));
                imageRepository.saveAllAndFlush(productImages);

                return modelMapper.map(response, PublicProductResponse.class);
        }

        @Override
        public void deleteProduct(Long id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("No product found."));

                // Product Attribute
                product.getProductAttributes().forEach(a -> {
                        Set<Product> oldProducts = a.getProducts();
                        oldProducts.remove(product);
                });
                attributeRepository.saveAllAndFlush(product.getProductAttributes());

                // Child Category
                if (product.getCategory() != null) {
                        product.getCategory().getProducts().remove(product);
                        categoryRepository.save(product.getCategory());
                }

                // Promotion Category
                if (product.getPromotion() != null) {
                        product.getPromotion().getProducts().remove(product);
                        promotionRepository.save(product.getPromotion());
                }
                if(product.getCarts() != null){
                        cartRepository.deleteAllInBatch(product.getCarts());
                }
                if(product.getFavorites() != null){
                        favoriteRepository.deleteAllInBatch(product.getFavorites());
                }
                if(product.getProductReviews() != null){
                        productReviewRepository.deleteAllInBatch(product.getProductReviews());
                }
                if(product.getImages() != null){
                        imageRepository.deleteAllInBatch(product.getImages());
                }
                productRepository.delete(product);
        }

        private String safeSubstring(String str, int beginIndex, int endIndex) {
                if (str == null) {
                        throw new IllegalArgumentException("String cannot be null");
                }

                // Ensure beginIndex is not negative and does not exceed string length
                if (beginIndex < 0) {
                        beginIndex = 0;
                }
                if (beginIndex > str.length()) {
                        beginIndex = str.length();
                }

                // Ensure endIndex is not negative and does not exceed string length
                if (endIndex < 0) {
                        endIndex = 0;
                }
                if (endIndex > str.length()) {
                        endIndex = str.length();
                }

                // Ensure beginIndex is not greater than endIndex
                if (beginIndex > endIndex) {
                        beginIndex = endIndex;
                }

                return str.substring(beginIndex, endIndex);
        }

        @Override
        public void increasePurchaseByOne(Long id) {
                productRepository.increasePurchasesById(id);
        }

        @Override
        public void importProduct(int importQuantity, Long id) {
                productRepository.updateInStockQuantityById(id, importQuantity);
        }

        @Override
        public void exportProduct(int exportQuantity, Long id) {
                productRepository.updateInStockQuantityById(id, -exportQuantity);
                productRepository.updateInSellQuantityById(id, exportQuantity);
        }

}
