package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation {

    @Test
    public void validate_Json_schema() {
        given()
                .baseUri("http://postman-echo.com")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200)
                .body(matchesJsonSchemaInClasspath("EchoGet.json"));

    }
}
