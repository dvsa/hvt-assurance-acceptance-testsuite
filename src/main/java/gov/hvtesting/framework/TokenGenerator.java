package gov.hvtesting.framework;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.json.JSONObject;

public class TokenGenerator {

    private String TOKEN_GENERATOR_HOST;
    private int TOKEN_GENERATOR_PORT;

    public TokenGenerator() {
        TOKEN_GENERATOR_HOST = PropertyManager.getInstance(true).getTokenGeneratorHost();
        TOKEN_GENERATOR_PORT = Integer.decode(PropertyManager.getInstance(true).getTokenGeneratorPort());
    }

    public void generateToken(String atfId) {
        JSONObject json = new JSONObject();
        json.put("atfId", atfId);
        Response response = given().baseUri(TOKEN_GENERATOR_HOST)

            .port(TOKEN_GENERATOR_PORT)
            .queryParam("atfId", atfId)
                .body(json.toString())
            .when()
            .post();
        response.then().statusCode(200);
    }
}
