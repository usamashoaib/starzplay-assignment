package com.starzplay.assignment.service.impl;

import com.starzplay.assignment.dto.PaymentPlanDTO;
import com.starzplay.assignment.repository.PaymentPlanRepository;
import com.starzplay.assignment.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.starzplay.assignment.entity.PaymentPlan;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentPlanServiceImpl implements PaymentPlanService {

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Override
    public Map<String, List<PaymentPlanDTO>> getPaymentPlansGroupedByDuration() {
        return paymentPlanRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        PaymentPlan::getDuration,
                        Collectors.mapping(plan -> new PaymentPlanDTO(
                                        plan.getId(), plan.getNetAmount(), plan.getTaxAmount(),
                                        plan.getGrossAmount(), plan.getCurrency(), plan.getDuration()),
                                Collectors.toList())
                ));
    }
}
