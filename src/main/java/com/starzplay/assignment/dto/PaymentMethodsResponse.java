package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentMethodsResponse {
    @JsonProperty("paymentMethods")
    private List<PaymentMethodDTO> paymentMethods;
}
