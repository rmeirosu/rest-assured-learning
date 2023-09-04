package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimplePojoTest {

    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public void BeforeClass() {
        io.restassured.builder.RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        requestSpecBuilder.setBaseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9");
        requestSpecBuilder.setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        requestSpecBuilder.setContentType("application/json;charset=utf-8");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        io.restassured.builder.ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void simple_pojo_example() throws JsonProcessingException {

        // with Constructor:
        SimplePojo simplePojo = new SimplePojo("value1", "value2");

        SimplePojo deserializedPojo =
            given()
                    .body(simplePojo)
            .when()
                    .post("/postSimplePojo")
            .then()
                    .spec(responseSpecification)
                    .extract().response().as(SimplePojo.class);

        ObjectMapper objectMapper = new ObjectMapper();
        // actual Json as string:
        String deserializedPojoStr = objectMapper.writeValueAsString(deserializedPojo);
        // expected Json as string:
        String simplePojoStr = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(deserializedPojoStr),
                equalTo(objectMapper.readTree(simplePojoStr)));
    }

}
