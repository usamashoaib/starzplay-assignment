package com.starzplay.assignment.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "payment_method")
@ToString(exclude = "paymentPlans")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentPlan> paymentPlans = new ArrayList<>();
}

