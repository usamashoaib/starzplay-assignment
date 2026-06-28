package com.starzplay.assignment.service.impl;

import com.starzplay.assignment.config.CacheConfig;
import com.starzplay.assignment.dto.PaymentMethodDTO;
import com.starzplay.assignment.dto.PaymentMethodRequestDTO;
import com.starzplay.assignment.dto.PaymentPlanDTO;
import com.starzplay.assignment.dto.PaymentPlanRequestDTO;
import com.starzplay.assignment.entity.PaymentMethod;
import com.starzplay.assignment.entity.PaymentPlan;
import com.starzplay.assignment.exception.ResourceNotFoundException;
import com.starzplay.assignment.repository.PaymentMethodRepository;
import com.starzplay.assignment.repository.PaymentPlanRepository;
import com.starzplay.assignment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.PAYMENT_METHODS, key = "'all'")
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        return paymentMethodRepository.findAll().stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.PAYMENT_METHODS, key = "'name:' + #name")
    public List<PaymentMethodDTO> getPaymentMethodsByName(String name) {
        return paymentMethodRepository.findByName(name).stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.PAYMENT_METHODS, key = "'country:' + #country")
    public List<PaymentMethodDTO> getPaymentMethodsByCountry(String country) {
        return paymentMethodRepository.findByCountry(country).stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.PAYMENT_METHODS, key = "'plan:' + #planId")
    public List<PaymentMethodDTO> getPaymentMethodsByPlanId(Integer planId) {
        // Returns the owning payment method, exposing only the matching plan
        return paymentPlanRepository.findById(planId)
                .map(plan -> {
                    PaymentMethod method = plan.getPaymentMethod();
                    PaymentMethodDTO dto = new PaymentMethodDTO(
                            method.getName(),
                            method.getDisplayName(),
                            method.getPaymentType(),
                            method.getCountry(),
                            List.of(new PaymentPlanDTO(
                                    plan.getId(), plan.getNetAmount(), plan.getTaxAmount(),
                                    plan.getGrossAmount(), plan.getCurrency(), plan.getDuration())));
                    return List.of(dto);
                })
                .orElse(List.of());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {CacheConfig.PAYMENT_METHODS, CacheConfig.PAYMENT_PLANS_BY_DURATION},
            allEntries = true)
    public List<PaymentMethodDTO> createPaymentMethods(List<PaymentMethodRequestDTO> requests) {
        List<PaymentMethod> entities = requests.stream()
                .map(this::toEntity)
                .toList();
        return paymentMethodRepository.saveAll(entities).stream()
                .map(PaymentMethodDTO::convertToDTO)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {CacheConfig.PAYMENT_METHODS, CacheConfig.PAYMENT_PLANS_BY_DURATION},
            allEntries = true)
    public PaymentMethodDTO updatePaymentMethod(Integer id, PaymentMethodRequestDTO request) {
        PaymentMethod existing = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("payment method not found for id " + id));

        existing.setName(request.getName());
        existing.setDisplayName(request.getDisplayName());
        existing.setPaymentType(request.getPaymentType());
        existing.setCountry(request.getCountry());

        // Replace the plans; orphanRemoval deletes the ones no longer present
        existing.getPaymentPlans().clear();
        if (request.getPaymentPlans() != null) {
            request.getPaymentPlans().stream()
                    .map(planReq -> toPlanEntity(planReq, existing))
                    .forEach(existing.getPaymentPlans()::add);
        }

        return PaymentMethodDTO.convertToDTO(paymentMethodRepository.save(existing));
    }

    private PaymentMethod toEntity(PaymentMethodRequestDTO request) {
        PaymentMethod method = new PaymentMethod();
        method.setName(request.getName());
        method.setDisplayName(request.getDisplayName());
        method.setPaymentType(request.getPaymentType());
        method.setCountry(request.getCountry());
        if (request.getPaymentPlans() != null) {
            request.getPaymentPlans().stream()
                    .map(planReq -> toPlanEntity(planReq, method))
                    .forEach(method.getPaymentPlans()::add);
        }
        return method;
    }

    private PaymentPlan toPlanEntity(PaymentPlanRequestDTO request, PaymentMethod method) {
        PaymentPlan plan = new PaymentPlan();
        plan.setNetAmount(request.getNetAmount());
        plan.setTaxAmount(request.getTaxAmount());
        plan.setGrossAmount(request.getGrossAmount());
        plan.setCurrency(request.getCurrency());
        plan.setDuration(request.getDuration());
        plan.setPaymentMethod(method);
        return plan;
    }
}
