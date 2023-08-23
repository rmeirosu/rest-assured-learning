package com.rest;

import io.restassured.http.Header;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;




public class Headers {
    /**
     * Headers contain metadata that is sent along with the request.
     * They can be used to determine the functionality or response
     * the server sends back to client.
     *
     * https://www.iana.org/assignments/message-headers/message-headers.xhtml
     */

    @Test
    public void multiple_headers() {

        Header header1 = new Header("header", "value2");
        Header header2 = new Header("x-mock-match-request-headers", "header2");
        io.restassured.http.Headers headers = new io.restassured.http.Headers(header1, header2);

        given()
                .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
                .headers(headers)
                // you can call header method multiple times for multiple headers:
                // .header(header1)
                // .header(header2)
                // or
                // .header("header","value2")
                // .header("x-mock-match-request-headers", "header2")
        .when()
                .get("/get")

        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiple_headers_withMap() {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header2");

        given()
                .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
                .headers(headers)

        .when()
                .get("/get")

        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiple_value_headers_inRequest() {

        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");
        io.restassured.http.Headers headers = new io.restassured.http.Headers(header1, header2);

        given()
                .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
                .headers(headers)
                .log().all()

        .when()
                .get("/get")

        .then()
                .log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void assert_response_headers() {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header2");

        given()
                .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
                .headers(headers)

        .when()
                .get("/get")

        .then()
                .log().all()
                .assertThat()
                    .statusCode(200)
                    .headers("responseHeader", "resValue2",
                    "X-RateLimit-Limit", "120");
//                    .header("responseHeader", "resValue2")
//                    .header("X-RateLimit-Limit", "120");
    }

    @Test
    public void extract_response_headers() {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header2");

        io.restassured.http.Headers extractedHeaders =
            given()
                    .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
                    .headers(headers)

            .when()
                    .get("/get")

            .then()
                    .assertThat()
                        .statusCode(200)
                        .extract().headers();

        for (Header header: extractedHeaders) {
            System.out.print("header name = " + header.getName() + "; ");
            System.out.println("header value = " + header.getValue());
            System.out.println("============");
        }

        System.out.println("extracted header name: " + extractedHeaders.get("responseHeader").getName());
        System.out.println("extracted header value: " + extractedHeaders.getValue("responseHeader"));
    }

    @Test
    public void extract_multivalue_response_header() {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header2");

        io.restassured.http.Headers extractedHeaders =
        given()
            .baseUri("https://01c13511-eb44-448c-8179-a0155fdb7dc7.mock.pstmn.io/")
            .headers(headers)

        .when()
            .get("/get")

        .then()
            .log().all()
            .assertThat().statusCode(200)
            .extract().headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader");

        for (String value: values) {
            System.out.println(value);
        }
    }
}
