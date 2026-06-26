package com.starzplay.assignment.service;

import com.starzplay.assignment.dto.PaymentMethodDTO;
import com.starzplay.assignment.dto.PaymentMethodRequestDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentMethodDTO> getAllPaymentMethods();
    List<PaymentMethodDTO> getPaymentMethodsByName(String name);
    List<PaymentMethodDTO> getPaymentMethodsByCountry(String country);
    List<PaymentMethodDTO> createPaymentMethods(List<PaymentMethodRequestDTO> requests);
    PaymentMethodDTO updatePaymentMethod(Integer id, PaymentMethodRequestDTO request);
}
