package com.testproject.testproject.tests.api.tests;

import com.testproject.testproject.dto.AuthorizationRequest;
import com.testproject.testproject.dto.AuthorizationResponse;
import com.testproject.testproject.dto.PlayerRegistrationRequest;
import com.testproject.testproject.dto.PlayerRegistrationResponse;
import com.testproject.testproject.statics.ApiPaths;
import com.testproject.testproject.statics.AuthorizationScope;
import com.testproject.testproject.statics.GrantType;
import com.testproject.testproject.tests.api.base.TestParent;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import java.util.Base64;

import static com.testproject.testproject.statics.PlayerDefaultCredentials.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest extends TestParent {
    private String tokenAccess;
    private Integer newPlayerId;
    private String newPlayerUsername;
    private String playerTokenAccess;

    @SneakyThrows
    @Test
    @Order(1)
    public void authorization() {
        var request = AuthorizationRequest.builder()
                .grant_type(GrantType.CLIENT_CREDENTIALS)
                .scope(AuthorizationScope.GUEST_DEFAULT)
                .build();

        var response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(apiCredentials.getBasicAuthenticationLogin(), apiCredentials.getBasicAuthenticationPassword())
                .log().all()
                .when()
                .body(objectMapper.writeValueAsString(request))
                .contentType(ContentType.JSON)
                .post(ApiPaths.AUTHORIZATION)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AuthorizationResponse.class);

        assertThat(response.getAccess_token())
                .isNotBlank();

        assertThat(response.getToken_type())
                .isNotBlank();

        tokenAccess = String.format("%s %s", response.getToken_type(), response.getAccess_token());
    }

    @SneakyThrows
    @Test
    @Order(2)
    public void registerUser() {
        newPlayerUsername = "test%s".formatted(RandomUtils.nextInt(1000, 100000));

        var request = PlayerRegistrationRequest.builder()
                .username(newPlayerUsername)
                .password_change(Base64.getEncoder().encodeToString(PASSWORD.getBytes()))
                .password_repeat(Base64.getEncoder().encodeToString(PASSWORD.getBytes()))
                .email("%s@gmail.com".formatted(RandomStringUtils.randomAlphabetic(10)))
                .build();

        var response = RestAssured.given()
                .log().all()
                .headers("Authorization", tokenAccess)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(request))
                .post(ApiPaths.PLAYER_REGISTRATION)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PlayerRegistrationResponse.class);

        assertThat(response.getCountry_id())
                .isNull();

        assertThat(response.getTimezone_id())
                .isNull();

        assertThat(response.getUsername())
                .isEqualTo(request.getUsername());

        assertThat(response.getEmail())
                .isEqualTo(request.getEmail());

        assertThat(response.getName())
                .isNull();

        assertThat(response.getSurname())
                .isNull();

        assertThat(response.getGender())
                .isNull();

        assertThat(response.getPhone_number())
                .isNull();

        assertThat(response.getBirthdate())
                .isNull();

        assertThat(response.getBonuses_allowed())
                .isTrue();

        assertThat(response.getIs_verified())
                .isFalse();

        newPlayerId = response.getId();
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void authorizeAsPlayer() {
        var request = AuthorizationRequest.builder()
                .grant_type(GrantType.PASSWORD)
                .username(newPlayerUsername)
                .password(Base64.getEncoder().encodeToString(PASSWORD.getBytes()))
                .build();

        var response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(apiCredentials.getBasicAuthenticationLogin(), apiCredentials.getBasicAuthenticationPassword())
                .log().all()
                .when()
                .body(objectMapper.writeValueAsString(request))
                .contentType(ContentType.JSON)
                .post(ApiPaths.AUTHORIZATION)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(AuthorizationResponse.class);

        assertThat(response.getAccess_token())
                .isNotBlank();

        assertThat(response.getToken_type())
                .isNotBlank();

        assertThat(response.getRefresh_token())
                .isNotBlank();

        playerTokenAccess = "%s %s".formatted(response.getToken_type(), response.getAccess_token());
    }

    @Test
    @Order(4)
    public void getDataAboutPlayer() {
        RestAssured.given()
                .log().all()
                .pathParam("id", newPlayerId)
                .headers("Authorization", playerTokenAccess)
                .get(ApiPaths.PLAYER_PROFILE)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(5)
    public void getDataAboutDifferentPlayer() {
        RestAssured.given()
                .log().all()
                .pathParam("id", 1)
                .headers("Authorization", playerTokenAccess)
                .get(ApiPaths.PLAYER_PROFILE)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
