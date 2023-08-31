package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class RequestPayloadComplexJson {

    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass() {

        io.restassured.builder.RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io")
                .addHeader("x-mock-match-request-body", "true")
//                .addHeader("X-Api-Key",
//                        "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
//                .setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
//                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType("application/json;charset=utf-8")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        io.restassured.builder.ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_payload_complex_json() {

        List<Integer> idArrayList = new ArrayList<Integer>();
        idArrayList.add(5);
        idArrayList.add(9);

        HashMap<String, Object> batterHashMap2 = new HashMap<String, Object>();
        batterHashMap2.put("id", idArrayList);
        batterHashMap2.put("type", "Chocolate");

        HashMap<String, Object> batterHashMap1 = new HashMap<String, Object>();
        batterHashMap1.put("id", "1001");
        batterHashMap1.put("type", "Regular");

        List<HashMap<String, Object>> batterArrayList = new ArrayList<HashMap<String, Object>>();
        batterArrayList.add(batterHashMap1);
        batterArrayList.add(batterHashMap2);

        HashMap<String, List<HashMap<String, Object>>> batterHashMap
                = new HashMap<String, List<HashMap<String, Object>>>();
        batterHashMap.put("batter", batterArrayList);

        List<String> typeArrayList = new ArrayList<String>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String, Object> toppingHashMap2 = new HashMap<String, Object>();
        toppingHashMap2.put("id", "5002");
        toppingHashMap2.put("type", typeArrayList);

        HashMap<String, Object> toppingHashMap1 = new HashMap<String, Object>();
        toppingHashMap1.put("id", "5001");
        toppingHashMap1.put("type", "None");

        List<HashMap<String, Object>> toppingArrayList = new ArrayList<HashMap<String, Object>>();
        toppingArrayList.add(toppingHashMap1);
        toppingArrayList.add(toppingHashMap2);

        HashMap<String, Object> mainHashMap = new HashMap<String, Object>();
        mainHashMap.put("id", "0001");
        mainHashMap.put("type", "donut");
        mainHashMap.put("name", "Cake");
        mainHashMap.put("ppu", 0.55);
        mainHashMap.put("batters", batterHashMap);
        mainHashMap.put("topping", toppingArrayList);


        given()
                .body(mainHashMap)
        .when()
                .post("/postComplexJson")
        .then()
                .spec(customResponseSpecification)
                .assertThat().body("msg", equalTo("Success"));
    }
}
