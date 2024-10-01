package com.starzplay.assignment.controller;

import com.starzplay.assignment.dto.PaymentMethodDTO;
import com.starzplay.assignment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/configuration/payment-methods")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getPaymentMethods(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country) {
        if (name != null) {
            return ResponseEntity.ok(paymentService.getPaymentMethodsByName(name));
        } else if (country != null) {
            return ResponseEntity.ok(paymentService.getPaymentMethodsByCountry(country));
        }
        return ResponseEntity.ok(paymentService.getAllPaymentMethods());
    }

}
