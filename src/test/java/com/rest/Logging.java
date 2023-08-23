package com.rest;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class Logging {

    @Test
    public void request_response_logging() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .log().headers()
        .when()
                .get("/workspaces")
        .then()
                .log().body()
                .assertThat().statusCode(200);
    }

    @Test
    public void request_response_log_ifError() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                // .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-")
                // uncomment above to obtain error
                .log().all()
        .when()
                .get("/workspaces")
        .then()
                .log().ifError()
                .assertThat().statusCode(200);
    }

    @Test
    public void request_response_log_if_validation_fail() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                // enable config logging to avoid duplicating log().ifValidationFails()
                // .log().ifValidationFails()
        .when()
                .get("/workspaces")
        .then()
                // .log().ifValidationFails()
                 .assertThat().statusCode(200);
                // .assertThat().statusCode(201);
                // uncomment above to obtain error
    }
}
