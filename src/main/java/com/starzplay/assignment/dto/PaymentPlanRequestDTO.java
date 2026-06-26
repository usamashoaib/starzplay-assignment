package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPlanRequestDTO {

    @JsonProperty("id")
    private Integer id;

    @NotNull(message = "netAmount is required")
    @JsonProperty("netAmount")
    private Double netAmount;

    @NotNull(message = "taxAmount is required")
    @JsonProperty("taxAmount")
    private Double taxAmount;

    @NotNull(message = "grossAmount is required")
    @JsonProperty("grossAmount")
    private Double grossAmount;

    @NotBlank(message = "currency is required")
    @JsonProperty("currency")
    private String currency;

    @NotBlank(message = "duration is required")
    @JsonProperty("duration")
    private String duration;
}
