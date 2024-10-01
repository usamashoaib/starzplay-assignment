package com.starzplay.assignment.service.impl;

import com.starzplay.assignment.dto.PaymentMethodDTO;
import com.starzplay.assignment.repository.PaymentMethodRepository;
import com.starzplay.assignment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        return paymentMethodRepository.findAll().stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethodsByName(String name) {
        return paymentMethodRepository.findByName(name).stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethodsByCountry(String country) {
        return paymentMethodRepository.findByCountry(country).stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }


}
