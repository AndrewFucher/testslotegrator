package com.testproject.testproject.bean;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ApiCredentials.class,
        HostsConfiguration.class,
        TestConfiguration.class
})
public class BeanConfiguration {
    @Bean
    public WebDriverManager webDriver() {
        var webDriverManager = WebDriverManager.chromedriver();
        webDriverManager.setup();
        return webDriverManager;
    }
}
