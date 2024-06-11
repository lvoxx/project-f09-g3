package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "'address'")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specific_address", length = 100, nullable = false)
    private String specificAddress;

    @Column(name = "ward", length = 30, nullable = false)
    private String ward;

    @Column(name = "province", length = 30, nullable = false)
    private String province;

    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @Column(name = "is_default")
    @Builder.Default
    private boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;
}
