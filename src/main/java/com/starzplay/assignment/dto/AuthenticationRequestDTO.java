package com.starzplay.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
