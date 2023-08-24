package com.rest;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationExample {

    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() {
        // request specification
        io.restassured.builder.RequestSpecBuilder requestSpecBuilder = new io.restassured.builder.RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        //response specification
        responseSpecification = RestAssured.expect().statusCode(200).contentType(ContentType.JSON);
    }

    @org.testng.annotations.Test
    public void validate_get_status_code() {
        get("/workspaces")
                .then().spec(responseSpecification)
                .log().all();
    }

    @Test
    public void validate_get_response_body() {
        Response response = get("/workspaces")
                .then().spec(responseSpecification)
                .log().all()
                .extract().response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }



}
