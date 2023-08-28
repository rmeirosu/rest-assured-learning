package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePut {

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
    public void validate_put_request_bdd() {
        String workspaceID = "b3b5a0b7-f1aa-4dec-9999-e28a3daa7856";
        String payload = "{\n" +
                "    \"workspace\" : {\n" +
                "        \"name\" : \"myFirstWorkspace BDD PUT\",\n" +
                "        \"type\" : \"personal\",\n" +
                "        \"description\" : \"This is updated by Rest Assured\"\n" +
                "    }\n" +
                "}";

        given()
                .body(payload)
        .when()
                .put("/workspaces/" + workspaceID)
        .then()
                .assertThat()
                .body("workspace.name", equalTo("myFirstWorkspace BDD PUT"),
                "workspace.id", Matchers.matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceID));
    }

}
