package com.rest;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RequestParameters {

    @Test
    public void single_query_parameter() {
        given()
                .baseUri("https://postman-echo.com")
//                .param("foo1", "bar1")
                .queryParam("foo1", "bar1")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void multiple_query_parameter() {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("foo1", "bar1");
        queryParams.put("foo2", "bar2");

        given()
                .baseUri("https://postman-echo.com")
//                .queryParam("foo1","bar1")
//                .queryParam("foo2","bar2")
// instead of chaining queryParam, use HashMaps:
                .queryParams(queryParams)
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void multiple_value_query_parameter() {
        given()
                .baseUri("https://postman-echo.com")
                .queryParam("foo1","bar1, bar2, bar3")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void path_parameter() {


        given()
                .baseUri("https://regres.in/api/users")
                .pathParam("userId","2")
                .log().all()
        .when()
                .get("/api/users{userId}")
        .then()
                .log().all();
//                .assertThat()
//                    .statusCode(200);
    }
}
