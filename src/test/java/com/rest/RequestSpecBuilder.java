package com.rest;

import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.Matchers.*;

public class RequestSpecBuilder {

    io.restassured.specification.RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass() {
//        requestSpecification =
//                with()
//                        .baseUri("https://api.postman.com")
//                        .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
//                        .log().all();

        // using requestSpecBuilder:

        io.restassured.builder.RequestSpecBuilder requestSpecBuilder = new io.restassured.builder.RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9");
        requestSpecBuilder.log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();


    }

    @org.testng.annotations.Test
    public void request_specification_example1() {
        // given().spec(requestSpecification) or
        given().spec(requestSpecification)
        .when()
                .get("/workspaces")
        .then()
                .log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void request_specification_example2() {
        // reusing the request specification
        given().spec(requestSpecification)
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
        Response response = given().spec(requestSpecification).header("dummyHeader", "dummyValue")
                .get("/workspaces").then().log().all().extract().response();

        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }
}