package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateGet {

    @Test
    public void validate_get_status_code() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
        .when()
                .get("/workspaces")
        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void validate_get_response_body() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
        .when()
                .get("/workspaces")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Team Workspace", "Personal Workspace"),
                        "workspaces.type", hasItems("team", "personal"),
                        "workspaces[0].name", equalTo("My Workspace"));
    }

    @Test
    public void extract_response() {
        Response res = given()
                                .baseUri("https://api.postman.com")
                                .header("X-Api-Key",
                                        "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                        .when()
                                .get("/workspaces")
                        .then()
                                .log().all()
                                .assertThat().statusCode(200)
                                .extract().response();
        System.out.println("response is: " + res.asString());
    }

    @Test
    public void extract_single_value_from_response() {
        String name = given()
                            .baseUri("https://api.postman.com")
                            .header("X-Api-Key",
                                "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                        .when()
                            .get("/workspaces")
                        .then()
//                            .log().all()
                            .assertThat().statusCode(200)
                            .extract().response().path("workspaces[0].name");
//                            .extract().response().asString();
        System.out.println("workspace name = " + name);

        // multiple assertion libraries:

//        assertThat(name, equalTo("My Workspace"));
        Assert.assertEquals(name, "My Workspace");

//        JsonPath jsonPath = new JsonPath(res.asString());
//        System.out.println("workspace name is: " + JsonPath.from(res).getString("workspaces[0].name"));
//        System.out.println("workspace name is: " + jsonPath.getString("workspaces[0].name"));
//        System.out.println("workspace name is: " + res.path("workspaces[0].name"));
    }

}


/*
"workspaces": [
        {
            "id": "f8b9b054-12af-49c9-870c-78e6d0c491f3",
            "name": "My Workspace",
            "type": "team",
            "visibility": "team"
        },
        {
            "id": "5d363a3b-8800-4e65-8ae1-a9f3318a616f",
            "name": "Team Workspace",
            "type": "team",
            "visibility": "team"
        },
        {
            "id": "13d038c2-1d34-4ac0-be3c-efc48861c38f",
            "name": "Personal Workspace",
            "type": "personal",
            "visibility": "personal"
        }
    ]
 */
