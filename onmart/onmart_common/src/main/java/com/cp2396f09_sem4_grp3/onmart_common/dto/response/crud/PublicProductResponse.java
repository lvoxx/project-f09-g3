package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PublicProductResponse {

    private Long id;
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("import_price")
    private BigDecimal importPrice;
    @JsonProperty("sell_price")
    private BigDecimal sellPrice;
    private int purchased;
    // TODO: Remember to set this if promotion is != null
    @JsonProperty("discount_percent")
    private int discountPercent;
    @JsonProperty("in_sell_quantity")
    private int inSellQuantity;
    @JsonProperty("in_stock_quantity")
    private int inStockQuantity;
    @JsonProperty("short_description")
    private String shortDescription;
    private String description;
    @JsonProperty("supplier_id")
    private Long supplierId;
    @JsonProperty("supplier_name")
    private String supplierName;
    @JsonProperty("is_featured")
    private boolean isFeatured;
    @JsonProperty("is_public")
    private boolean isPublic;
    // TODO: Remember to set this
    @JsonProperty("images_url")
    private List<String> imagesUrl;
    @JsonProperty("product_attributes")
    private Set<ProductAtrributeResponse> productAttributes;
    private PromotionProductResponse promotion;
    private ChildCategoryResponse category;
}
