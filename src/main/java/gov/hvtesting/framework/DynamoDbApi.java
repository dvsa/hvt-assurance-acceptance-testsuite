package gov.hvtesting.framework;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.response.Response;

public class DynamoDbApi {

    private static final String READ_API_HOST = "http://localhost";
    private static final int READ_API_PORT = 3000;
    private static final String ATF_DATA_TABLE = "AuthorisedTestingFacilities";

    public DynamoDbApi(){

    }
    public Response getAtfAvailabilityData(String atfId)  {
        Response response = given().baseUri(READ_API_HOST)
            .port(READ_API_PORT)
            .basePath(ATF_DATA_TABLE +"/" + atfId)
            .queryParam("keyName", "id")
            .when()
            .get();
        response.then().statusCode(200);
        return response;
    }
}
