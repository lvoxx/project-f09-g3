package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_attribute")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "price_difference", precision = 10, scale = 2)
    private BigDecimal priceDifference;

    @ManyToOne
    @JoinColumn(name = "product_attribute_group_id")
    @JsonBackReference
    private ProductAttributeGroup productAttributeGroup;

    @ManyToMany(cascade = { CascadeType.ALL, CascadeType.MERGE })
    @JoinTable(name = "product_attribute_product", joinColumns = @JoinColumn(name = "product_attribute_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @Builder.Default
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();
}
