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

-Negative tests to be covered:

    Headers
        without Content-Type header
        without Accept header
        without Content-Type and Accept headers
        with invalid Content-Type header
        with invalid Accept header
        with invalid Content-Type and Accept headers

    Request body
        without firstname key
        without lastname key
        without totalprice key
        without depositpaid key
        without checkin key
        without checkout key
        without additionalneeds key
        without all keys
        with invalid firstname key
        with invalid lastname key
        with invalid totalprice key
        with invalid depositpaid key
        with invalid checkin key
        with invalid checkout key
        with invalid additionalneeds key
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
    RequestSpecification reqSpecInvalidContentType;
    RequestSpecification reqSpecInvalidAccept;
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
    File payloadEmpty = new File("src/test/resources/CreateBooking/payloadEmpty.json");
    File payloadInvalidFirstName = new File("src/test/resources/CreateBooking/payloadInvalidFirstName.json");
    File payloadInvalidLastName = new File("src/test/resources/CreateBooking/payloadInvalidLastName.json");
    File payloadInvalidTotalprice = new File("src/test/resources/CreateBooking/payloadInvalidTotalprice.json");
    File payloadInvalidDepositPaid = new File("src/test/resources/CreateBooking/payloadInvalidDepositPaid.json");
    File payloadInvalidCheckIn = new File("src/test/resources/CreateBooking/payloadInvalidCheckIn.json");
    File payloadInvalidCheckOut = new File("src/test/resources/CreateBooking/payloadInvalidCheckOut.json");
    File payloadInvalidAdditionalNeeds = new File("src/test/resources/CreateBooking/payloadInvalidAdditionalNeeds.json");

    @BeforeClass
    public void BeforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        reqSpec = requestSpecBuilder.build();

        RequestSpecBuilder requestSpecBuilderInvalidCT = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.BINARY)
                .log(LogDetail.ALL);
        reqSpecInvalidContentType = requestSpecBuilderInvalidCT.build();

        RequestSpecBuilder requestSpecBuilderInvalidAccept = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.BINARY)
                .log(LogDetail.ALL);
        reqSpecInvalidAccept = requestSpecBuilderInvalidAccept.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        resSpec = responseSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilderNegative = new ResponseSpecBuilder()
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
    // the field is treated as optional, 200 OK without it
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

    // no keys
    @Test
    public void negative_no_keys() {
        given(reqSpec)
                .body(payloadEmpty)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }


    // invalid firstname key
    @Test
    public void negative_invalid_firstname() {
        given(reqSpec)
                .body(payloadInvalidFirstName)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid lastname key
    @Test
    public void negative_invalid_lastname() {
        given(reqSpec)
                .body(payloadInvalidLastName)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid totalprice key
    @Test
    public void negative_invalid_totalprice() {
        given(reqSpec)
                .body(payloadInvalidTotalprice)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpec)
                .assertThat().statusCode(200)
                .body("totalprice", Matchers.nullValue());
    }

    // invalid depositpaid key
    // there is an issue here, the documentation states the field is boolean
    // when using string or int, the request is accepted though
    @Test
    public void negative_invalid_depositpaid() {
        given(reqSpec)
                .body(payloadInvalidDepositPaid)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid checkin key
    @Test
    public void negative_invalid_checkin() {
        given(reqSpec)
                .body(payloadInvalidCheckIn)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid checkout key
    @Test
    public void negative_invalid_checkout() {
        given(reqSpec)
                .body(payloadInvalidCheckOut)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid additionalneeds key
    // the field is treated as optional, 200 OK without it
    @Test
    public void negative_invalid_additionalneeds() {
        given(reqSpec)
                .body(payloadInvalidAdditionalNeeds)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpec)
                .assertThat().statusCode(200);
    }

    // invalid header content type
    @Test
    public void negative_invalid_contentype() {
        given(reqSpecInvalidContentType)
                .body(payload)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(500);
    }

    // invalid header accept
    @Test
    public void negative_invalid_accept() {
        given(reqSpecInvalidAccept)
                .body(payload)
        .when()
                .post(baseUri)
        .then()
                .spec(resSpecNegative)
                .assertThat().statusCode(418)
                .body(Matchers.equalTo("I'm a Teapot"));
    }

}
