package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EPaymentMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_history")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory extends AbstractAuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    @MapsId
    @JsonBackReference
    private Order order;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(length = 3)
    private String currency;

    @Column(length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private EPaymentMethod paymentMethod;
}