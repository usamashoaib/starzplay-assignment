package com.starzplay.assignment.repository;

import com.starzplay.assignment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    List<PaymentMethod> findByName(String name);
    List<PaymentMethod> findByCountry(String country);
}
