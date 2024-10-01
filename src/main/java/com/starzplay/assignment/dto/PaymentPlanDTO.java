package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentPlanDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("netAmount")
    private Double netAmount;
    @JsonProperty("taxAmount")
    private Double taxAmount;
    @JsonProperty("grossAmount")
    private Double grossAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("duration")
    private String duration;
}
