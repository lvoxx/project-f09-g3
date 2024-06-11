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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "export_stock_invoice_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportStockInvoiceDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    private int quantity;

    // Left table
    @ManyToOne
    @JoinColumn(name = "export_stock_invoice_id")
    @JsonBackReference
    private ExportStockInvoice exportStockInvoice;

}
