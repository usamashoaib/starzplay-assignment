package com.starzplay.assignment.repository;

import com.starzplay.assignment.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Integer> {
    List<PaymentPlan> findByDuration(String duration);
}
