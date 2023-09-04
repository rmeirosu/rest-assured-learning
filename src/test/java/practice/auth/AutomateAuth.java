/*
Create a new auth token to use for access to the PUT and DELETE for a booking.

    endpoint URL: https://restful-booker.herokuapp.com/auth
    CURL:
        curl -X POST \
        https://restful-booker.herokuapp.com/auth \
        -H 'Content-Type: application/json' \
        -d '{
        "username" : "admin",
        "password" : "password123"
        }'

    Negative scenarios to be covered:

    Headers
        without Content-Type header
        with invalid Content-Type header

    Request body
        without username
        without password
        without username and password
        with invalid username
        with invalid password
        with invalid username and password
*/

package practice.auth;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomateAuth {

    RequestSpecification reqSpecHappy;
    ResponseSpecification resSpecHappy;
    RequestSpecification reqInvalidContentType;

    String baseUri = "https://restful-booker.herokuapp.com";
    String basePath = "/auth";

    String payload = "{\n" +
            "    \"username\": \"admin\",\n" +
            "    \"password\": \"password123\"\n" +
            "}";

    String payloadNoUsername = "{\n" +
            "    \"password\": \"password123\"\n" +
            "}";

    String payloadNoPassword = "{\n" +
            "    \"username\": \"admin\"\n" +
            "}";

    String payloadNoUserNoPass = "{\n" +
            "}";

    String payloadInvalidUsername = "{\n" +
            "    \"password\": \"password123_invalid\"\n" +
            "}";

    String payloadInvalidPassword = "{\n" +
            "    \"username\": \"admin_invalid\"\n" +
            "}";

    String payloadInvalidUserAndPass = "{\n" +
            "    \"username\": \"admin_invalid\",\n" +
            "    \"password\": \"password123_invalid\"\n" +
            "}";

    @BeforeClass
    public void BeforeClass() {

        RequestSpecBuilder reqSpecBuilderHappy = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        reqSpecHappy = reqSpecBuilderHappy.build();

        RequestSpecBuilder reqSpecBuilderInvalidContentType = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.TEXT)
                .log(LogDetail.ALL);
        reqInvalidContentType = reqSpecBuilderInvalidContentType.build();

        ResponseSpecBuilder resSpecBuilderHappy = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        resSpecHappy = resSpecBuilderHappy.build();

    }

    @Test
    public void happy_path(){
        given(reqSpecHappy)
                .body(payload)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200);
    }

    @Test
    public void negative_invalid_ContentType() {
        given(reqInvalidContentType)
                .body(payload)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_without_ContentType() {
        // TODO: how to send header without ContentType?
    }

    @Test
    public void negative_request_body_without_username() {
        given(reqSpecHappy)
                .body(payloadNoUsername)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_request_body_without_password() {
        given(reqSpecHappy)
                .body(payloadNoPassword)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_request_body_without_user_or_pass() {
        given(reqSpecHappy)
                .body(payloadNoUserNoPass)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_request_body_invalid_username() {
        given(reqSpecHappy)
                .body(payloadInvalidUsername)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_request_body_invalid_password() {
        given(reqSpecHappy)
                .body(payloadInvalidPassword)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void negative_request_body_invalid_user_password() {
        given(reqSpecHappy)
                .body(payloadInvalidUserAndPass)
        .when()
                .post(baseUri + basePath)
        .then()
                .spec(resSpecHappy)
                .assertThat().statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

}
