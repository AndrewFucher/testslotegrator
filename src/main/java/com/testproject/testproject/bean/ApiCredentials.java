package com.testproject.testproject.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api-credentials")
public class ApiCredentials {
    private String basicAuthenticationLogin;
    private String basicAuthenticationPassword = "";
}
