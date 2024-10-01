package com.starzplay.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "payment_plan")
@ToString
public class PaymentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "net_amount", nullable = false)
    private Double netAmount;

    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount;

    @Column(name = "gross_amount", nullable = false)
    private Double grossAmount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "duration", nullable = false)
    private String duration;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;
}

