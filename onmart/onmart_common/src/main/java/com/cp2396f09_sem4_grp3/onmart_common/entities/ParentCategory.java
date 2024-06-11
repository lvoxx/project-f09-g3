package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parent_category")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(40)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(200)")
    private String imageName;

    @Lob
    @Column(columnDefinition = "LONGBLOB", name = "image_data")
    private byte[] imageData;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChildCategory> childCategory = new ArrayList<>();

}
