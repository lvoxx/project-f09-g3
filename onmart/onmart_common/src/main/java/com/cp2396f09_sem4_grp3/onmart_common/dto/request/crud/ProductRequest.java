package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

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
public class ProductRequest {
    private String name;
    private BigDecimal import_price;
    private BigDecimal sell_price;
    private String description;
    // TODO: get ID
    private Long supplier_id;
    // TODO: get ID
    private Long category_id;
    private String is_featured;
    private String is_published;
    // TODO: make new
    private List<MultipartFile> images;
    // TODO: get ID
    private Set<Long> product_attribute_ids;
}
