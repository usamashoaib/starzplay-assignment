package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starzplay.assignment.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentMethodDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("country")
    private String country;
    @JsonProperty("paymentPlans")
    private List<PaymentPlanDTO> paymentPlans;


    public static PaymentMethodDTO convertToDTO(PaymentMethod method) {
        return new PaymentMethodDTO(
                method.getName(),
                method.getDisplayName(),
                method.getPaymentType(),
                method.getCountry(),
                method.getPaymentPlans().stream()
                        .map(plan -> new PaymentPlanDTO(
                                plan.getId(), plan.getNetAmount(), plan.getTaxAmount(),
                                plan.getGrossAmount(), plan.getCurrency(), plan.getDuration()))
                        .toList()
        );
    }
}
