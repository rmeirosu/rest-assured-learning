package practice.auth;

/*
Create positive and negative tests for CreateBooking endpoint.
    endpoint URL: https://restful-booker.herokuapp.com/booking
    CURL:
    curl -X POST \
        https://restful-booker.herokuapp.com/booking \
          -H 'Content-Type: application/json' \
          -d '{
            "firstname" : "Jim",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2018-01-01",
                "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
        }'
 */

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateBooking {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    ResponseSpecification resSpecNegative;

    String baseUri = "https://restful-booker.herokuapp.com/booking";

    File payload = new File("src/test/resources/CreateBooking/payload.json");
    File payloadNoFirstName = new File("src/test/resources/CreateBooking/payloadNoFirstName.json");
    File payloadNoLastName = new File("src/test/resources/CreateBooking/payloadNoLastName.json");
    File payloadNoTotalPrice = new File("src/test/resources/CreateBooking/payloadNoTotalPrice.json");
    File payloadNoDepositPaid = new File("src/test/resources/CreateBooking/payloadNoDepositPaid.json");
    File payloadNoBookingDates = new File("src/test/resources/CreateBooking/payloadNoBookingDates.json");
    File payloadNoCheckIn = new File("src/test/resources/CreateBooking/payloadNoCheckIn.json");
    File payloadNoCheckOut = new File("src/test/resources/CreateBooking/payloadNoCheckOut.json");
    File payloadNoAdditionalNeeds = new File("src/test/resources/CreateBooking/payloadNoAdditionalNeeds.json");

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

        ResponseSpecBuilder responseSpecBuilderNegative = new ResponseSpecBuilder()
                .expectStatusCode(500)
                .expectContentType(ContentType.TEXT)
                .log(LogDetail.ALL);
        resSpecNegative = responseSpecBuilderNegative.build();
    }

    @Test
    public void create_booking_happy() {
        given(reqSpec)
            .body(payload)
        .when()
            .post(baseUri)
        .then()
            .spec(resSpec)
            .assertThat().statusCode(200)
            .body("bookingid", Matchers.notNullValue(),
                    "booking.firstname", equalTo("Jim"),
                    "booking.lastname", equalTo("Brown"),
                    "booking.totalprice", equalTo(111),
                    "booking.depositpaid", equalTo(true),
                    "booking.bookingdates.checkin", equalTo("2018-01-01"),
                    "booking.bookingdates.checkout", equalTo("2019-01-01"),
                    "booking.additionalneeds", equalTo("Breakfast")
                    );
    }

    // no first name
    @Test
    public void negative_no_firstname() {
        given(reqSpec)
                .body(payloadNoFirstName)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no last name
    @Test
    public void negative_no_lastname() {
        given(reqSpec)
                .body(payloadNoLastName)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no total price
    @Test
    public void negative_no_totalprice() {
        given(reqSpec)
                .body(payloadNoTotalPrice)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no deposit paid
    @Test
    public void negative_no_depositpaid() {
        given(reqSpec)
                .body(payloadNoDepositPaid)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no booking dates
    @Test
    public void negative_no_bookingdates() {
        given(reqSpec)
                .body(payloadNoBookingDates)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no checkin
    @Test
    public void negative_no_checkin() {
        given(reqSpec)
                .body(payloadNoCheckIn)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no checkout
    @Test
    public void negative_no_checkout() {
        given(reqSpec)
                .body(payloadNoCheckOut)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // no additional needs - optional
    @Test
    public void negative_no_additional_needs() {
        given(reqSpec)
                .body(payloadNoAdditionalNeeds)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpec)
                .assertThat().statusCode(200);
    }

}
