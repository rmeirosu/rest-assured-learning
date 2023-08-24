package com.rest;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.Matchers.*;

public class RequestSpecificationExample {

    io.restassured.specification.RequestSpecification requestSpecification;

    // create an RequestSpecification object with the request data to be reused through the tests
    @BeforeClass
    public void beforeClass() {
        requestSpecification =
                with()
                        .baseUri("https://api.postman.com")
                        .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                        .log().all();

    }

    @org.testng.annotations.Test
    public void request_specification_example1() {
        // given().spec(requestSpecification) or
        given(requestSpecification)
        .when()
                .get("/workspaces")
        .then()
                .log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void request_specification_example2() {
        // reusing the request specification
        given(requestSpecification)
        .when()
                .get("/workspaces")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Team Workspace", "Personal Workspace"),
                        "workspaces.type", hasItems("team", "personal"),
                        "workspaces[0].name", equalTo("My Workspace"));
    }

    @org.testng.annotations.Test
    public void bdd_to_non_bdd() {
        // Response response = requestSpecification.get("/workspaces"); // without logging

        // log all from response:
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }
}