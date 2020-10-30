package gov.hvtesting.framework;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;

import io.restassured.response.Response;

public class DynamoDbApi {

    private String READ_API_HOST;
    private int READ_API_PORT;
    private static final String ATF_DATA_TABLE = "AuthorisedTestingFacilities";

    public DynamoDbApi(){
        READ_API_HOST = PropertyManager.getInstance(true).getReadApiHost();
        READ_API_PORT = Integer.decode(PropertyManager.getInstance(true).getReadApiPort());
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

    public Response getNearestAtfs()  {
        Response response = given().baseUri(READ_API_HOST)
            .port(READ_API_PORT)
            .basePath(ATF_DATA_TABLE)
            .when()
            .get();
        response.then().statusCode(200);
        return response;
    }

    public String getAtfId()  {
        Response response = getNearestAtfs();
        String id = response.getBody().jsonPath().getString("Items.id[0]");
        return id;
    }

    public String getAtfId(String atfName)  {
        Response response = getNearestAtfs();
        HashMap<String, String> aa = new HashMap<String, String>();
        List<HashMap<String,String>> items = response.getBody().jsonPath().getList("Items");
         items.stream().forEach(x->aa.put(x.get("name"), x.get("id")));
         String id = aa.get(atfName);
        return id;
    }
}
