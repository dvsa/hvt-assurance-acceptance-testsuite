package gov.hvtesting.framework.dbModels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="PostcodesCoordinates")
public class PostcodesCoordinatesItem {

    private String postcode;
    private String latitude;
    private String longitude;

    @DynamoDBHashKey(attributeName = "postcode")
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    @DynamoDBAttribute(attributeName="lat")
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    @DynamoDBAttribute(attributeName="long")
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
}
