package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class JacksonApiJsonObject {

    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public void BeforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                        .expectStatusCode(200)
                                .expectContentType(ContentType.JSON)
                                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_post_request_payload_as_map() throws JsonProcessingException {
        HashMap<String, Object> mainObject = new HashMap<>();
        HashMap<String, String> nestedObject = new HashMap<>();

        nestedObject.put("name", "myThirdWorkspace2");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");

        mainObject.put("workspace", nestedObject);

        // Create Object Mapper object with Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainObject);

        given()
                .body(mainObject)
        .when()
                .post("/workspaces")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("workspace.name", equalTo("myThirdWorkspace2"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

}
