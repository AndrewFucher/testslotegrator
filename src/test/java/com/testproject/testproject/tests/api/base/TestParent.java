package com.testproject.testproject.tests.api.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.testproject.TestprojectApplication;
import com.testproject.testproject.bean.ApiCredentials;
import com.testproject.testproject.bean.HostsConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(classes = TestprojectApplication.class)
@TestInstance(PER_CLASS)
public abstract class TestParent {
    @Autowired
    protected ApiCredentials apiCredentials;
    @Autowired
    protected HostsConfiguration hostsConfiguration;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeAll
    public void setUpBaseUrl() {
        RestAssured.baseURI = hostsConfiguration.getApiTestsUrl();
    }
}
