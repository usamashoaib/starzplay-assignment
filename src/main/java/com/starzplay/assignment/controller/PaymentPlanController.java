package com.starzplay.assignment.controller;

import com.starzplay.assignment.dto.PaymentPlanDTO;
import com.starzplay.assignment.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/configuration/payment-plans")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

    @Autowired
    public PaymentPlanController(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    @GetMapping("/duration")
    public ResponseEntity<Map<String, List<PaymentPlanDTO>>> getPaymentPlansGroupedByDuration() {
        return ResponseEntity.ok(paymentPlanService.getPaymentPlansGroupedByDuration());
    }
}
