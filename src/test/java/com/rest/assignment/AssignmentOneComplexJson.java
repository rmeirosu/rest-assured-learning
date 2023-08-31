package com.rest.assignment;

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


public class AssignmentOneComplexJson {

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
    public void validate_post_request_assignment_one() {

        List<Integer> rgbaArrayList1 = new ArrayList<>();
        rgbaArrayList1.add(255);
        rgbaArrayList1.add(255);
        rgbaArrayList1.add(255);
        rgbaArrayList1.add(1);

        HashMap<String, Object> codeHashMap1 = new HashMap<>();
        codeHashMap1.put("rgba", rgbaArrayList1);
        codeHashMap1.put("hex", "#000");

        List<Integer> rgbaArrayList2 = new ArrayList<>();
        rgbaArrayList2.add(0);
        rgbaArrayList2.add(0);
        rgbaArrayList2.add(0);
        rgbaArrayList2.add(1);

        HashMap<String, Object> codeHashMap2 = new HashMap<String, Object>();
        codeHashMap2.put("rgba", rgbaArrayList2);
        codeHashMap2.put("hex", "#FFF");

        HashMap<String, Object> colorsHashMap1 = new HashMap<String, Object>();
        colorsHashMap1.put("color", "black");
        colorsHashMap1.put("category", "hue");
        colorsHashMap1.put("type", "primary");
        colorsHashMap1.put("code", codeHashMap1);

        HashMap<String, Object> colorsHashMap2 = new HashMap<String, Object>();
        colorsHashMap2.put("color", "white");
        colorsHashMap2.put("category", "value");
        colorsHashMap2.put("code", codeHashMap2);

        //colors

        List<HashMap<String, Object>> colorsArrayList = new ArrayList<HashMap<String, Object>>();
        colorsArrayList.add(colorsHashMap1);
        colorsArrayList.add(colorsHashMap2);

        HashMap<String, List<HashMap<String, Object>>> colorsHashMap
                = new HashMap<String, List<HashMap<String, Object>>>();
        colorsHashMap.put("colors", colorsArrayList);

        given()
            .body(colorsHashMap)
        .when()
            .post("/postAssignmentOne")
        .then()
            .spec(customResponseSpecification)
            .assertThat().body("msg", equalTo("Assignment One Success"));
    }
}