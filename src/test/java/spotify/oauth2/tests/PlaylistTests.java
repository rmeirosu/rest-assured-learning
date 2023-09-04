package spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PlaylistTests {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    String spotifyUri = "https://api.spotify.com";
    String spotifyBasePath = "/v1";
    String accessToken = "BQCGfN26Mvb1FuicStpfhRvVsdz605A6TkJGPybWFtTVm-KNuCq8u2u0q5lYgmBM_d5QPxaSrkKKUdH3KMYFGFNvjO9mPHXsipA-bi3zaQfwP_1QM9bLnSD0McyCLnFSHLt072bTEY4eH3MqyUUcWbYELe8t4UVjtYNd9OK3FLgce3_6GJWU798hpYBBaqhWACac9Q";
    String userId = "21xftrskbaafr427gu5vrzi5a";


    @BeforeClass
    public void BeforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(spotifyUri)
                .setBasePath(spotifyBasePath)
                .addHeader("Authorization", "Bearer" + accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createAPlaylist() {

        String payload = "{\n" +
                "   \"name\": \"New Playlist\",\n" +
                "   \"description\": \"New playlist description\",\n" +
                "   \"public\": false\n" +
                "}";

        given(requestSpecification)
                .body(payload)
        .when()
                .post("/users/" + userId)
        .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);
    }
}
