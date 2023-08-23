package com.rest;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Collection matchers (List, Array, Map, etc)
 * ===========================================
 * hasItem() -> verify if element is present in the collection
 * not(hasItem()) -> check if element is NOT present in the collection
 * hasItems() -> verify if ANY elements are present in the collection, in ANY order
 * contains() -> verify if ALL elements are present in the collection in SPECIFIED order
 * containsInAnyOrder() -> verify if ALL elements are present in ANY order
 * empty() -> verify if collection is empty
 * not(emptyArray()) -> verify if collection is not empty
 * hasSize() -> verify the size of the collection
 * everyItem(startsWith()) -> verify if every item in the collection starts with specified string
 *
 * hasKey() -> Map -> verify if Map has the specified key (value is not verified)
 * hasValue() -> Map -> verify if Map has at least one key matching specified value
 * hasEntry() -> Map -> verify if Map has the specified kev/value pair
 * equalTo(Collections.EMPTY_MAP) -> verify if Map is empty
 * allOf() -> verify all matchers
 * anyOf() -> verify any matchers
 *
 * Numbers
 * =======
 * greaterThanOrEqualTo()
 * lessThan()
 * lessThanOrEqualTo()
 *
 * Strings
 * =======
 * containsString()
 * emptyString()
 */

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
                .body("workspaces.name", is(not(empty())));
                // verify if returned collection is NOT empty
    }

    @Test
    public void validate_get_response_body_hamcrest_hasSize() {
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
                .body("workspaces.name", hasSize(3));
        // verify if returned collection size is 3
    }

    @Test
    public void validate_get_response_body_hamcrest_hasEntry() {
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
                .body("workspaces[0]", hasEntry("name", "My Workspace"));
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
