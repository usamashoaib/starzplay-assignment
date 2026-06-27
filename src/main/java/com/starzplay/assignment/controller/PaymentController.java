package com.starzplay.assignment.controller;

import com.starzplay.assignment.dto.PaymentMethodDTO;
import com.starzplay.assignment.dto.PaymentMethodRequestDTO;
import com.starzplay.assignment.dto.PaymentMethodsResponse;
import com.starzplay.assignment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1.0/configuration/payment-methods")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<PaymentMethodsResponse> getPaymentMethods(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer id) {
        List<PaymentMethodDTO> methods;
        if (name != null) {
            methods = paymentService.getPaymentMethodsByName(name);
        } else if (country != null) {
            methods = paymentService.getPaymentMethodsByCountry(country);
        } else if (id != null) {
            methods = paymentService.getPaymentMethodsByPlanId(id);
        } else {
            methods = paymentService.getAllPaymentMethods();
        }
        return ResponseEntity.ok(new PaymentMethodsResponse(methods));
    }

    @PostMapping
    public ResponseEntity<PaymentMethodsResponse> createPaymentMethods(
            @RequestBody List<@Valid PaymentMethodRequestDTO> requests) {
        List<PaymentMethodDTO> created = paymentService.createPaymentMethods(requests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PaymentMethodsResponse(created));
    }

    @PutMapping
    public ResponseEntity<PaymentMethodsResponse> updatePaymentMethod(
            @RequestParam("payment-methods") Integer id,
            @Valid @RequestBody PaymentMethodRequestDTO request) {
        PaymentMethodDTO updated = paymentService.updatePaymentMethod(id, request);
        return ResponseEntity.ok(new PaymentMethodsResponse(List.of(updated)));
    }
}
