package com.testproject.testproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {
    private String token_type;
    private Integer expires_in;
    private String access_token;
    private String refresh_token;
}
