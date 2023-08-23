package com.rest;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchers {

    @Test
    public void validate_get_response_body_hamcrest_hasItems() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Team Workspace"));
                // verify any of the elements ARE PRESENT
    }

    @Test
    public void validate_get_response_body_hamcrest_contains() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", contains("My Workspace", "Team Workspace", "Personal Workspace"));
                // verify all elements are present in SPECIFIED order
    }

    @Test
    public void validate_get_response_body_hamcrest_containsInAnyOrder() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", containsInAnyOrder("Personal Workspace",
                        "My Workspace", "Team Workspace"));
                // verify all elements are present in ANY order
    }

    @Test
    public void validate_get_response_body_hamcrest_empty() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-64e34cf637d0cc00298b3759-b056c75f549f5ecc0aa579429315249de9")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                // .body("workspaces.name", empty());
                // verify if returned collection is empty
                .body("workspace.name", is(not(empty())));
                // verify if returned collection is NOT empty
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
