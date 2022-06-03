package com.testproject.testproject.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tests")
public class TestConfiguration {
    private Integer driverConditionTimoutInSeconds = 10;
}
