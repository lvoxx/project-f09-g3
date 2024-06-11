package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(200)")
    private String name;

    @Lob
    @Column(columnDefinition = "LONGBLOB", name = "image_data")
    private byte[] imageData;

    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_images_id")
    @JsonBackReference
    private Product productImages;

    @Column(name = "product_thumb_id")
    @Builder.Default
    private Long productThumbId = 0L;
}
