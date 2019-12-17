package homework;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RequestSpec {


    private static String uuid;


    private RequestSpecification defaultRequestSpecification() {
//        baseURI = "localhost:8080/";

        RequestSpecBuilder generalRequest = new RequestSpecBuilder();

        generalRequest
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON);

        return generalRequest.build();
    }

    public void createGist(String endpoint, JSONObject jsonObject, String expectedAuthor, String expectedContent, String expectedDate) {
        Response response =
                given()
                        .spec(defaultRequestSpecification())
                        .body(jsonObject.toString())
                        .when()
                        .post(endpoint)
                        .then()
                        .log().ifValidationFails()
                        .statusCode(200)
                        .and()
                        .body("author", equalTo(expectedAuthor),
                                "uuid", notNullValue(),
                                "type", equalTo("INFO"),
                                "content", equalTo(expectedContent),
                                "validUntil", equalTo(expectedDate)
                        ).extract().response();

        uuid = response.path("uuid");

    }

    public String getUuid() {
        return uuid;
    }

    public void getGist(String endpoint, String expectedAuthor, String expectedContent, String expectedDate, int expectedStatusCode) {
        given()
                .spec(defaultRequestSpecification())
                .when()
                .get(endpoint)
                .then()
                .log().ifValidationFails()
                .statusCode(expectedStatusCode)
                .body("author", equalTo(expectedAuthor),
                        "uuid", equalTo(uuid),
                        "type", equalTo("INFO"),
                        "content", equalTo(expectedContent),
                        "validUntil", equalTo(expectedDate)
                );
    }

    public void updateGist(String endpoint, JSONObject jsonObject, String expectedAuthor, String expectedContent, String expectedDate) {
        Response response =
                given()
                        .spec(defaultRequestSpecification())
                        .body(jsonObject.toString())
                        .when()
                        .put(endpoint)
                        .then()
                        .log().ifValidationFails()
                        .statusCode(200)
                        .and()
                        .body("author", equalTo(expectedAuthor),
                                "uuid", equalTo(uuid),
                                "type", equalTo("INFO"),
                                "content", equalTo(expectedContent),
                                "validUntil", equalTo(expectedDate)
                        ).extract().response();

    }

    public void deleteGist(String endpoint) {
                given()
                        .spec(defaultRequestSpecification())
                        .when()
                        .delete(endpoint)
                        .then()
                        .log().ifValidationFails()
                        .statusCode(200);
    }
}