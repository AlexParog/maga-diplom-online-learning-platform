package ru.parog.magauserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {

    private boolean isValid;
    private Long userId;
    private String username;
    private Set<String> roles;
}
