package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("description")
    private String description;
    @JsonProperty("httpStatusCode")
    private String httpStatusCode;
    @JsonProperty("requestId")
    private String requestId;
}
