/*
A simple health check endpoint to confirm whether the API is up and running.
    endpoint URL: https://restful-booker.herokuapp.com/ping

    CURL:
    curl -i https://restful-booker.herokuapp.com/ping
 */
package practice.auth;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HealthCheck {

    @Test
    public void happy_path(){
        given()
                .baseUri("https://restful-booker.herokuapp.com")
                .log().all()
        .when()
                .get("/ping")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(201)
                    .body(containsString("Created"));
    }
}
