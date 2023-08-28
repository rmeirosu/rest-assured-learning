package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePost {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeader("X-Api-Key",
                        "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_bdd() {
        String payload = "{\n" +
                "    \"workspace\" : {\n" +
                "        \"name\" : \"myFirstWorkspace\",\n" +
                "        \"type\" : \"personal\",\n" +
                "        \"description\" : \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";

        given()
                .body(payload)
        .when()
                .post("/workspaces")
        .then()
                .assertThat()
                .body("workspace.name", equalTo("myFirstWorkspace"),
                        "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"));

    }


    @Test
    public void validate_post_request_non_bdd() {
        String payload = "{\n" +
                "    \"workspace\" : {\n" +
                "        \"name\" : \"myFirstWorkspace\",\n" +
                "        \"type\" : \"personal\",\n" +
                "        \"description\" : \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";

        Response response = with()
                                .body(payload)
                                .post("/workspaces");
        assertThat(response.<String>path("workspace.name"),equalTo("myFirstWorkspace"));
        assertThat(response.<String>path("workspace.id"),Matchers.matchesPattern("^[a-z0-9-]{36}$"));

    }


}
