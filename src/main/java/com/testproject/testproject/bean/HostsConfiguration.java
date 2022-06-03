package com.testproject.testproject.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api-hosts")
public class HostsConfiguration {
    private String apiTestsUrl;
    private String webTestsUrl;
}
