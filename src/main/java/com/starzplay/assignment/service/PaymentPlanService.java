package com.starzplay.assignment.service;

import com.starzplay.assignment.dto.PaymentPlanDTO;

import java.util.List;
import java.util.Map;

public interface PaymentPlanService {
    Map<String, List<PaymentPlanDTO>> getPaymentPlansGroupedByDuration();
}
