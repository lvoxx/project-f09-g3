package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "'product'")
public class Product extends AbstractAuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(200)", nullable = false, length = 200)
    private String name;

    @Column(name = "short_name", length = 40)
    private String shortName;

    @Column(name = "import_price", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal importPrice = BigDecimal.valueOf(0.0);

    @Column(name = "sell_price", precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Builder.Default
    private int purchased = 0;

    @Column(name = "in_sell_quantity")
    @Builder.Default
    private int inSellQuantity = 0;

    @Column(name = "in_stock_quantity")
    @Builder.Default
    private int inStockQuantity = 0;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Lob
    private String description;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "is_featured")
    private boolean isFeatured;

    @Column(name = "is_public")
    private boolean isPublic;

    // Left Data Table
    @OneToMany(mappedBy = "productImages", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();

    @ManyToMany(mappedBy = "products", cascade = { CascadeType.ALL, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @Builder.Default
    @JsonBackReference
    private Set<ProductAttribute> productAttributes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private ChildCategory category;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    @JsonBackReference
    private Promotion promotion;

    // Right Data Table
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<ProductReview> productReviews = new ArrayList<>();

}
