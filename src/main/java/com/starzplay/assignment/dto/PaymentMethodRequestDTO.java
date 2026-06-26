package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodRequestDTO {

    @NotBlank(message = "missing payment method name")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "displayName is required")
    @JsonProperty("displayName")
    private String displayName;

    @NotBlank(message = "paymentType is required")
    @JsonProperty("paymentType")
    private String paymentType;

    @NotBlank(message = "country is required")
    @JsonProperty("country")
    private String country;

    @Valid
    @JsonProperty("paymentPlans")
    private List<PaymentPlanRequestDTO> paymentPlans = new ArrayList<>();
}
