package practice;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetBooking {
/*
Create positive and negative tests for GetBooking endpoint.

    endpoint URL: https://restful-booker.herokuapp.com/booking/:id
    CURL: curl -i https://restful-booker.herokuapp.com/booking/1

Negative tests to be covered:
    Headers
        without Accept header
        with invalid Accept header
    Url Parameter
        without id parameter
        with invalid id parameter
 */

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    String baseUri = "https://restful-booker.herokuapp.com/booking";
    String bookingId = "1";

    @BeforeClass
    public void BeforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        reqSpec = requestSpecBuilder.build();


        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        resSpec = responseSpecBuilder.build();

    }

    @Test
    public void get_booking_happy() {
        // Expected: return specific details for bookingid 1
        given(reqSpec)
        .when()
                .get(baseUri + "/" + bookingId)
        .then()
                .spec(resSpec)
                .assertThat().statusCode(200);
    }

    @Test
    public void negative_without_id() {
        // Expected: return all the bookings
        given(reqSpec)
        .when()
                .get(baseUri)
        .then()
                .spec(resSpec)
                .assertThat().statusCode(200)
                .body(Matchers.anything("bookingid"));
    }

    @Test
    public void negative_invalid_id() {
        // Expected: 404 error
        given(reqSpec)
        .when()
                .get(baseUri + "/" + "invalid_id")
        .then()
                .assertThat().statusCode(404);
    }

    // TODO: Negative:
    // Headers
    // without Accept header
    // with invalid Accept header


}
