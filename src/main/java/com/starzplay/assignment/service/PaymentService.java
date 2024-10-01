package com.starzplay.assignment.service;

import com.starzplay.assignment.dto.PaymentMethodDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentMethodDTO> getAllPaymentMethods();
    List<PaymentMethodDTO> getPaymentMethodsByName(String name);
    List<PaymentMethodDTO> getPaymentMethodsByCountry(String country);
}
